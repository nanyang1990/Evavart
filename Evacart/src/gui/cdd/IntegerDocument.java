package gui.cdd;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

  public class IntegerDocument extends PlainDocument {
    JTextField jt;
    int maxColumns;
    public IntegerDocument(JTextField aJTextField, int numColumns) {
      super();
      jt = aJTextField;
      maxColumns = numColumns;
    }

    public void insertString(int offset,
         String string, AttributeSet attributes)
         throws BadLocationException {

       if (string == null) {
         return;
       } else {
         String newValue;
         int length = getLength();
         if (length == 0) {
           newValue = string.substring(0,1);
         } else {
           String currentContent = getText(0, length);
           StringBuffer currentBuffer = new StringBuffer(currentContent);
           if ( string.length() + currentContent.length() > maxColumns) {
             string = string.substring(0,maxColumns-currentContent.length());
             }
           currentBuffer.insert(offset, string);
           newValue = currentBuffer.toString();
         }
         try {
           Integer.parseInt(newValue);
           super.insertString(offset, string, attributes);
           if (jt.getText().length() == maxColumns && jt.getCaretPosition() == maxColumns)
              jt.transferFocus();
         } catch (NumberFormatException exception) {
         }
       }
     }
   }
