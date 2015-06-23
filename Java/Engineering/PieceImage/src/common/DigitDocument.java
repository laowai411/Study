package common;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * 限制文本输入
 * */
@SuppressWarnings("serial")
public class DigitDocument extends PlainDocument {

	private int limitedLength;
	
	public DigitDocument(int limitedLength) {
		this.limitedLength = limitedLength;
	}

	@Override
	public void insertString(int offset, String str, AttributeSet a)
			throws BadLocationException {
		try {
			if (str == null)
				return;
			if ((this.getLength() + str.length()) <= limitedLength) // 限制字符长度
			{
				char[] upper = str.toCharArray();
				int length = 0;
				for (int i = 0; i < upper.length; i++) {
					if (Character.isDigit(upper[i])) {
						upper[length++] = upper[i];
					}
				}
				String numStr = new String(upper, 0, length);
				super.insertString(offset, numStr, a);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
