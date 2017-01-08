# coding:utf-8
import os
import struct
import time
import zlib
from xml.etree.cElementTree import parse

# 找到所有的cvt文件(不包括拆分后的)
def findAllResFile(cusPath, cusList):
	cusPath = str(cusPath)
	cusPath = cusPath.replace("/", "\\")

	if cusPath[-1] != "\\":
		cusPath = cusPath + "\\"

	fileList = os.listdir(cusPath)

	for x in fileList:
		tempPath = cusPath + x
		if os.path.isfile(tempPath):
			#排除常规非资源文件和地图块,缩略图
			if(tempPath.find(".svn") == -1 and tempPath.find(".exe") == -1 and tempPath.find(".json") and tempPath.find(".db") == -1) and\
				(tempPath.find("\\map\\") == -1 or (tempPath.find("swf") == -1 and tempPath.find("front_atf") == -1 and tempPath.find("front.swf") == -1)):
					cusList.append(tempPath)
		else:
			findAllResFile(cusPath + x, cusList)


# 计算一个cvt文件解压后在内存中的大小
def calcMemory(cusPath):
	size = 0

	cvtFile = open(cusPath, "rb")
	fileData = cvtFile.read()
	fileSize = cvtFile.tell()
	cvtFile.close()

	# 读取帧数据的长度
	frameDataLen = int(fileData[0:4].hex(), base=16)
	# 解析帧数据,计算面积
	index = 4
	while index < frameDataLen:
		#print("x=", int(fileData[index:index+4].hex(), base=16))
		#print("y=", int(fileData[index+4:index+8].hex(), base=16))
		width = int(fileData[index + 8:index + 12].hex(), base=16)
		height = int(fileData[index + 12:index + 16].hex(), base=16)
		#print("width=", width)
		#print("height=", height)
		strHeadLen = int(fileData[index + 16:index + 18].hex(), base=16)
		#print("str head=", strHeadLen)
		#print("str body=", str(fileData[index+18:index+18+strHeadLen]))
		index = index + 18 + strHeadLen
		size = size + width * height * 4
	return size


#写入fileTime
def writeFileTime(cusFileTimePath, cusFileList, cusResivionDic):
	fileTime = open(cusFileTimePath, "wb")
	fileStr = bytearray()
	tempFileStr = ""
	for url in cusFileList:
		tempSize = calcMemory(url)
		url = url.replace(os.getcwd(), "")
		url = url.replace("\\", "/")
		#把路径转为字节数组
		tempUrl = url.encode("utf-8")
		tempLen = len(tempUrl)
		# 写入路径头(无符号短整型)
		fileStr += struct.pack("<H", len(tempUrl))
		#写入路径
		fileStr += struct.pack("<"+str(tempLen)+"s", tempUrl)
		#写入版本号
		fileStr += struct.pack("<i", cusResivionDic[url])
		#写入占用内存大小
		fileStr += struct.pack("i", tempSize)
		tempFileStr += url + "\t\t" + str(tempSize) + "\n"
		print(url, tempSize)
	fileTime.write(zlib.compress(fileStr))
	fileTime.flush()
	fileTime.close()

	tempFileTime  = open(cusFileTimePath+".txt", "w")
	tempFileTime.write(tempFileStr)
	tempFileTime.flush()
	tempFileTime.close()
	pass


#解析从SVN到处的文件信息xml
def parseSvnInfoXML(cusXMLList, cusRevisionDic):
	for cusPath in cusXMLList:
		xml = parse(cusPath)
		rootURL = ""
		for entry in xml.iterfind("entry"):
			if entry.attrib["kind"] == "file":
				tempURL = entry.findtext["url"]
				if rootURL == "":
					rootURL = entry.findtext["repository/root"] + "/res/assets/"
				tempURL = tempURL.replace(rootURL, "")
				cusRevisionDic[tempURL] = entry.attrib["revision"]

#获取所有的SVN版本号xml
def findAllRevisionXML(cusPath, cusList):
	cusPath = str(cusPath)
	cusPath = cusPath.replace("/", "\\")
	if cusPath[-1] != "\\":
		cusPath = cusPath + "\\"
	fileList = os.listdir(cusPath)
	for filePath in fileList:
		tempPath = cusPath + filePath
		if os.path.isfile(tempPath) and tempPath.find(".xml") > -1:
			cusList.append(tempPath)
		else:
			findAllRevisionXML(cusPath + filePath, cusList)
	pass

starTime = time.time()
revisionDic = {}
revisionFileList = []
findAllRevisionXML("", revisionFileList)
parseSvnInfoXML(revisionFileList, revisionDic)
cvtFileList = []
findAllResFile(os.getcwd(), cvtFileList)
writeFileTime(os.getcwd()+"/fileTime.xx", cvtFileList, revisionDic)
print(time.time() - starTime)

