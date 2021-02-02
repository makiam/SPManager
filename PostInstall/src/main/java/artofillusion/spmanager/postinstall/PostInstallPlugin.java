/*
 *  Copyright (C) 2019 by Maksim Khramov

   This program is free software; you can redistribute it and/or modify it under the
   terms of the GNU General Public License as published by the Free Software
   Foundation; either version 2 of the License, or (at your option) any later version.

   This program is distributed in the hope that it will be useful, but WITHOUT ANY
   WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
   PARTICULAR PURPOSE.  See the GNU General Public License for more details.
*/

package artofillusion.spmanager.postinstall;

import artofillusion.EmptyPluginImplementation;
import artofillusion.LayoutWindow;
import artofillusion.ui.Translate;
import buoy.widget.BLabel;
import buoy.widget.BScrollPane;
import buoy.widget.BStandardDialog;
import buoy.widget.BTextArea;
import buoy.widget.Widget;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


public class PostInstallPlugin extends EmptyPluginImplementation
{
    private final List<String> ok = new ArrayList<>(128);
    private final List<String> err = new ArrayList<>(128);

    private static final DirectoryStream.Filter<Path> lockFileFilter = new DirectoryStream.Filter<Path>() {

        @Override
        public boolean accept(Path entry) throws IOException {
            return true;
        }

    };

    @Override
    public void onApplicationStarting(Object... args)
    {
        
    }

    @Override
    protected void onSceneWindowCreated(Object... args) {
        if (!err.isEmpty())
        {
            BTextArea txt = new BTextArea(5, 45);
            txt.setEditable(false);

            for(String item: err)
                txt.append(item + "\n");

            BScrollPane detail = new BScrollPane(txt, BScrollPane.SCROLLBAR_NEVER, BScrollPane.SCROLLBAR_AS_NEEDED);

            BLabel message = Translate.label("postinstall:errMsg");

            BStandardDialog errDlg = new BStandardDialog("PostInstall", new Widget[] { message, detail }, BStandardDialog.WARNING);
            errDlg.showMessageDialog((LayoutWindow)args[0]);
            err.clear();
        }

        if (!ok.isEmpty())
        {
            BTextArea txt = new BTextArea(5, 45);
            txt.setEditable(false);

            for(String oks: ok)
                txt.append(oks+"\n");

            BScrollPane detail = new BScrollPane(txt, BScrollPane.SCROLLBAR_NEVER, BScrollPane.SCROLLBAR_AS_NEEDED);
            BLabel message = Translate.label("postinstall:okMsg");
            BLabel restart = Translate.label("postinstall:restartMsg");

            BStandardDialog okDlg = new BStandardDialog("PostInstall: ", new Widget[] { message, restart, detail }, BStandardDialog.INFORMATION);
            okDlg.showMessageDialog((LayoutWindow)args[0]);
            ok.clear();
        }
    }



}
