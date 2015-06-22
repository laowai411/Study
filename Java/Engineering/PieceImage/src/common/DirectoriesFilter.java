/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import javax.swing.filechooser.FileFilter;

/**
 *
 * @author AngryPotato
 */
public class DirectoriesFilter extends FileFilter{
    
   @Override
public boolean accept(java.io.File f) {
    if (f.isDirectory())
    {
        return true;
    }
    return false;
  } 
  @Override
public String getDescription(){
    return "文件夹";
  }
}
