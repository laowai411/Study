/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fileUtil;

import javax.swing.filechooser.FileFilter;

/**
 *
 * @author AngryPotato
 */
public class ExcelFilter extends FileFilter {

    public boolean accept(java.io.File f) {
        if (f.isDirectory()) {
            return true;
        }
        if(f.getName().toLowerCase().endsWith(".xls") ||f.getName().toLowerCase().endsWith(".xlsx"))
        {
            return true;
        }
        return false;
    }

    public String getDescription() {
        return "excel";
    }
}
