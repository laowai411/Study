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
public class DirectoriesFilter extends FileFilter{
    
   public boolean accept(java.io.File f) {
    if (f.isDirectory())
    {
        return true;
    }
    return false;
  } 
  public String getDescription(){
    return "文件夹";
  }
}
