package util
{
	import events.ParamEvent;
	
	import flash.events.Event;
	import flash.events.EventDispatcher;
	import flash.events.FileListEvent;
	import flash.filesystem.File;
	import flash.net.FileFilter;
	
	/**
	 * 文件选择器
	 * */
	public class FileChooser extends EventDispatcher
	{
		/**
		 * 类型-文件夹
		 * */
		public static const TYPE_DIR:int = 0;
		/**
		 * 类型-文件
		 * */
		public static const TYPE_FILE:int = 1;
		/**
		 * jpg
		 * */
		public static var FILTERS_JPG:FileFilter = new FileFilter("*.jpg", "*.jpg");
		/**
		 * png
		 * */
		public static var FILTERS_PNG:FileFilter = new FileFilter("*.png", "*.png");
		
		/**
		 * 起始路径
		 * */
		private var gSrcFile:File;
		
		public function FileChooser()
		{
		}
		
		/**
		 * 选择一个文件夹
		 * */
		public function chooseDir(cusPath:String, cusTitle:String):void
		{
			gSrcFile = new File(cusPath);
			if(gSrcFile.exists && gSrcFile.isDirectory)
			{
				gSrcFile.addEventListener(Event.SELECT, onSelecteDirHandler);
				gSrcFile.browseForDirectory(cusTitle);
			}
		}
		
		/**
		 * 选择了一个文件夹
		 * */
		private function onSelecteDirHandler(e:Event):void
		{
			this.dispatchEvent(new ParamEvent(Event.SELECT, {"type":TYPE_DIR, "data":e.target}));
		}		
		
		/**
		 * 选择多个文件
		 * */
		public function chooseFiles(cusPath:String, cusTitle:String, cusFilters:Array, cusOnlyOne:Boolean=true):void
		{
			gSrcFile = new File(cusPath);
			if(gSrcFile.exists && gSrcFile.isDirectory)
			{
				if(cusOnlyOne)
				{
					gSrcFile.addEventListener(Event.SELECT, onSelecteFileHandler);
					gSrcFile.browseForOpen(cusTitle, cusFilters);
				}
				else
				{
					gSrcFile.addEventListener(FileListEvent.SELECT_MULTIPLE, onSelecteMulFileHandler);
					gSrcFile.browseForOpenMultiple(cusTitle, cusFilters);
				}
			}
		}
		
		/**
		 * 选择了一个文件
		 * */
		private function onSelecteFileHandler(e:Event):void
		{
			this.dispatchEvent(new ParamEvent(Event.SELECT, {"type":TYPE_FILE, "data":e.target}));
		}
		
		/**
		 * 选择了多个文件
		 * */
		private function onSelecteMulFileHandler(e:FileListEvent):void
		{
			this.dispatchEvent(new ParamEvent(Event.SELECT, {"type":TYPE_FILE, "data":e.files}));
		}
		
		/**
		 * dispose
		 * */
		public function dispose():void
		{
			gSrcFile.removeEventListener(Event.SELECT, onSelecteFileHandler);
			gSrcFile.removeEventListener(FileListEvent.SELECT_MULTIPLE, onSelecteMulFileHandler);
			gSrcFile.removeEventListener(Event.SELECT, onSelecteDirHandler);
			gSrcFile = null;
		}
	}
}