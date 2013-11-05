/*
 * $Id$
 * The Umask Calculator
 * Copyright (C) 2010 Lloyd S. Dilley <lloyd@dilley.me>
 * http://www.dilley.me/
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

/**
 * @author Lloyd S. Dilley
 */

package me.dilley.umaskcalculator;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public final class UmaskCalculator extends Activity
{
  private static boolean initialRun=true;

  private static byte userMask=0;
  private static byte groupMask=0;
  private static byte otherMask=0;
  private static byte fileUserBits=0;
  private static byte fileGroupBits=0;
  private static byte fileOtherBits=0;
  private static byte dirUserBits=0;
  private static byte dirGroupBits=0;
  private static byte dirOtherBits=0;
  
  private static String fileUserPerms="----";
  private static String fileGroupPerms="---";
  private static String fileOtherPerms="---";
  private static String dirUserPerms="d---";
  private static String dirGroupPerms="---";
  private static String dirOtherPerms="---";

  private static Button clear=null;

  private static ImageButton upUserBits=null;
  private static ImageButton downUserBits=null;
  private static ImageButton upGroupBits=null;
  private static ImageButton downGroupBits=null;
  private static ImageButton upOtherBits=null;
  private static ImageButton downOtherBits=null;

  private static EditText userBits=null;
  private static EditText groupBits=null;
  private static EditText otherBits=null;
  private static EditText fileUser=null;
  private static EditText fileGroup=null;
  private static EditText fileOther=null;
  private static EditText dirUser=null;
  private static EditText dirGroup=null;
  private static EditText dirOther=null;
  private static EditText fsBits=null;
  private static EditText dsBits=null;

  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    clear=(Button)this.findViewById(R.id.clear);

    upUserBits=(ImageButton)this.findViewById(R.id.upUserBits);
    downUserBits=(ImageButton)this.findViewById(R.id.downUserBits);
    upGroupBits=(ImageButton)this.findViewById(R.id.upGroupBits);
    downGroupBits=(ImageButton)this.findViewById(R.id.downGroupBits);
    upOtherBits=(ImageButton)this.findViewById(R.id.upOtherBits);
    downOtherBits=(ImageButton)this.findViewById(R.id.downOtherBits);

    userBits=(EditText)this.findViewById(R.id.userBits);
    groupBits=(EditText)this.findViewById(R.id.groupBits);
    otherBits=(EditText)this.findViewById(R.id.otherBits);

    fileUser=(EditText)this.findViewById(R.id.fouBits);
    fileGroup=(EditText)this.findViewById(R.id.fogBits);
    fileOther=(EditText)this.findViewById(R.id.fooBits);
    dirUser=(EditText)this.findViewById(R.id.douBits);
    dirGroup=(EditText)this.findViewById(R.id.dogBits);
    dirOther=(EditText)this.findViewById(R.id.dooBits);

    fsBits=(EditText)this.findViewById(R.id.fsBits);
    dsBits=(EditText)this.findViewById(R.id.dsBits);

    upUserBits.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View v)
      {
        if(initialRun)
        {
          groupBits.setText("0");
          otherBits.setText("0");
          initialRun=false;
        }

        if(userMask==7)
        {
          userMask=0;
          updatePermissions();
        }
        else
        {
          userMask++;
       	  updatePermissions();
       	}
       	userBits.setText(Byte.toString(userMask));
      }
    });

    downUserBits.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View v)
      {
        if(initialRun)
        {
          groupBits.setText("0");
          otherBits.setText("0");
          initialRun=false;
        }

        if(userMask==0)
       	{
       	  userMask=7;
       	  updatePermissions();
       	}
       	else
       	{
          userMask--;
       	  updatePermissions();
      	}
       	userBits.setText(Byte.toString(userMask));
      }
    });

    upGroupBits.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View v)
      {
        if(initialRun)
        {
          userBits.setText("0");
          otherBits.setText("0");
          initialRun=false;
        }

        if(groupMask==7)
        {
          groupMask=0;
          updatePermissions();
        }
        else
       	{
          groupMask++;
          updatePermissions();
       	}
       	groupBits.setText(Byte.toString(groupMask));
      }
    });

    downGroupBits.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View v)
      {
        if(initialRun)
        {
          userBits.setText("0");
          otherBits.setText("0");
          initialRun=false;
        }

        if(groupMask==0)
        {
       	  groupMask=7;
       	  updatePermissions();
       	}
       	else
       	{
          groupMask--;
      	  updatePermissions();
       	}
       	groupBits.setText(Byte.toString(groupMask));
      }
    });

    upOtherBits.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View v)
      {
        if(initialRun)
        {
          userBits.setText("0");
          groupBits.setText("0");
          initialRun=false;
        }

     	if(otherMask==7)
      	{
       	  otherMask=0;
       	  updatePermissions();
      	}
       	else
       	{
          otherMask++;
       	  updatePermissions();
       	}
      	otherBits.setText(Byte.toString(otherMask));
      }
    });

    downOtherBits.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View v)
      {
        if(initialRun)
        {
          userBits.setText("0");
          groupBits.setText("0");
          initialRun=false;
        }

     	if(otherMask==0)
      	{
       	  otherMask=7;
       	  updatePermissions();
       	}
      	else
       	{
          otherMask--;
       	  updatePermissions();
       	}
       	otherBits.setText(Byte.toString(otherMask));
      }
    });

    clear.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View v)
      {
        clearValues();
      }
    });
  }

  private static final void updatePermissions()
  {
    if(userMask==0)
    {
      fileUserBits=6;
      fileUser.setText(Byte.toString(fileUserBits));
      dirUserBits=7;
      dirUser.setText(Byte.toString(dirUserBits));
      fileUserPerms="-rw-";
      fsBits.setText(fileUserPerms + fileGroupPerms + fileOtherPerms);
      dirUserPerms="drwx";
      dsBits.setText(dirUserPerms + dirGroupPerms + dirOtherPerms);
    }
    else if(userMask==1)
    {
      fileUserBits=6;
      fileUser.setText(Byte.toString(fileUserBits));
      dirUserBits=6;
      dirUser.setText(Byte.toString(dirUserBits));
      fileUserPerms="-rw-";
      fsBits.setText(fileUserPerms + fileGroupPerms + fileOtherPerms);
      dirUserPerms="drw-";
      dsBits.setText(dirUserPerms + dirGroupPerms + dirOtherPerms);
    }
    else if(userMask==2)
    {
      fileUserBits=4;
      fileUser.setText(Byte.toString(fileUserBits));
      dirUserBits=5;
      dirUser.setText(Byte.toString(dirUserBits));
      fileUserPerms="-r--";
      fsBits.setText(fileUserPerms + fileGroupPerms + fileOtherPerms);
      dirUserPerms="dr-x";
      dsBits.setText(dirUserPerms + dirGroupPerms + dirOtherPerms);
    }
    else if(userMask==3)
    {
      fileUserBits=4;
      fileUser.setText(Byte.toString(fileUserBits));
      dirUserBits=4;
      dirUser.setText(Byte.toString(dirUserBits));
      fileUserPerms="-r--";
      fsBits.setText(fileUserPerms + fileGroupPerms + fileOtherPerms);
      dirUserPerms="dr--";
      dsBits.setText(dirUserPerms + dirGroupPerms + dirOtherPerms);
    }
    else if(userMask==4)
    {
      fileUserBits=2;
      fileUser.setText(Byte.toString(fileUserBits));
      dirUserBits=3;
      dirUser.setText(Byte.toString(dirUserBits));
      fileUserPerms="--w-";
      fsBits.setText(fileUserPerms + fileGroupPerms + fileOtherPerms);
      dirUserPerms="d-wx";
      dsBits.setText(dirUserPerms + dirGroupPerms + dirOtherPerms);
    }
    else if(userMask==5)
    {
      fileUserBits=2;
      fileUser.setText(Byte.toString(fileUserBits));
      dirUserBits=2;
      dirUser.setText(Byte.toString(dirUserBits));
      fileUserPerms="--w-";
      fsBits.setText(fileUserPerms + fileGroupPerms + fileOtherPerms);
      dirUserPerms="d-w-";
      dsBits.setText(dirUserPerms + dirGroupPerms + dirOtherPerms);
    }
    else if(userMask==6)
    {
      fileUserBits=0;
      fileUser.setText(Byte.toString(fileUserBits));
      dirUserBits=1;
      dirUser.setText(Byte.toString(dirUserBits));
      fileUserPerms="----";
      fsBits.setText(fileUserPerms + fileGroupPerms + fileOtherPerms);
      dirUserPerms="d--x";
      dsBits.setText(dirUserPerms + dirGroupPerms + dirOtherPerms);
    }
    else if(userMask==7)
    {
      fileUserBits=0;
      fileUser.setText(Byte.toString(fileUserBits));
      dirUserBits=0;
      dirUser.setText(Byte.toString(dirUserBits));
      fileUserPerms="----";
      fsBits.setText(fileUserPerms + fileGroupPerms + fileOtherPerms);
      dirUserPerms="d---";
      dsBits.setText(dirUserPerms + dirGroupPerms + dirOtherPerms);
    }

    if(groupMask==0)
    {
      fileGroupBits=6;
      fileGroup.setText(Byte.toString(fileGroupBits));
      dirGroupBits=7;
      dirGroup.setText(Byte.toString(dirGroupBits));
      fileGroupPerms="rw-";
      fsBits.setText(fileUserPerms + fileGroupPerms + fileOtherPerms);
      dirGroupPerms="rwx";
      dsBits.setText(dirUserPerms + dirGroupPerms + dirOtherPerms);
    }
    else if(groupMask==1)
    {
      fileGroupBits=6;
      fileGroup.setText(Byte.toString(fileGroupBits));
      dirGroupBits=6;
      dirGroup.setText(Byte.toString(dirGroupBits));
      fileGroupPerms="rw-";
      fsBits.setText(fileUserPerms + fileGroupPerms + fileOtherPerms);
      dirGroupPerms="rw-";
      dsBits.setText(dirUserPerms + dirGroupPerms + dirOtherPerms);
    }
    else if(groupMask==2)
    {
      fileGroupBits=4;
      fileGroup.setText(Byte.toString(fileGroupBits));
      dirGroupBits=5;
      dirGroup.setText(Byte.toString(dirGroupBits));
      fileGroupPerms="r--";
      fsBits.setText(fileUserPerms + fileGroupPerms + fileOtherPerms);
      dirGroupPerms="r-x";
      dsBits.setText(dirUserPerms + dirGroupPerms + dirOtherPerms);
    }
    else if(groupMask==3)
    {
      fileGroupBits=4;
      fileGroup.setText(Byte.toString(fileGroupBits));
      dirGroupBits=4;
      dirGroup.setText(Byte.toString(dirGroupBits));
      fileGroupPerms="r--";
      fsBits.setText(fileUserPerms + fileGroupPerms + fileOtherPerms);
      dirGroupPerms="r--";
      dsBits.setText(dirUserPerms + dirGroupPerms + dirOtherPerms);
    }
    else if(groupMask==4)
    {
      fileGroupBits=2;
      fileGroup.setText(Byte.toString(fileGroupBits));
      dirGroupBits=3;
      dirGroup.setText(Byte.toString(dirGroupBits));
      fileGroupPerms="-w-";
      fsBits.setText(fileUserPerms + fileGroupPerms + fileOtherPerms);
      dirGroupPerms="-wx";
      dsBits.setText(dirUserPerms + dirGroupPerms + dirOtherPerms);
    }
    else if(groupMask==5)
    {
      fileGroupBits=2;
      fileGroup.setText(Byte.toString(fileGroupBits));
      dirGroupBits=2;
      dirGroup.setText(Byte.toString(dirGroupBits));
      fileGroupPerms="-w-";
      fsBits.setText(fileUserPerms + fileGroupPerms + fileOtherPerms);
      dirGroupPerms="-w-";
      dsBits.setText(dirUserPerms + dirGroupPerms + dirOtherPerms);
    }
    else if(groupMask==6)
    {
      fileGroupBits=0;
      fileGroup.setText(Byte.toString(fileGroupBits));
      dirGroupBits=1;
      dirGroup.setText(Byte.toString(dirGroupBits));
      fileGroupPerms="---";
      fsBits.setText(fileUserPerms + fileGroupPerms + fileOtherPerms);
      dirGroupPerms="--x";
      dsBits.setText(dirUserPerms + dirGroupPerms + dirOtherPerms);
    }
    else if(groupMask==7)
    {
      fileGroupBits=0;
      fileGroup.setText(Byte.toString(fileGroupBits));
      dirGroupBits=0;
      dirGroup.setText(Byte.toString(dirGroupBits));
      fileGroupPerms="---";
      fsBits.setText(fileUserPerms + fileGroupPerms + fileOtherPerms);
      dirGroupPerms="---";
      dsBits.setText(dirUserPerms + dirGroupPerms + dirOtherPerms);
    }

    if(otherMask==0)
    {
      fileOtherBits=6;
      fileOther.setText(Byte.toString(fileOtherBits));
      dirOtherBits=7;
      dirOther.setText(Byte.toString(dirOtherBits));
      fileOtherPerms="rw-";
      fsBits.setText(fileUserPerms + fileGroupPerms + fileOtherPerms);
      dirOtherPerms="rwx";
      dsBits.setText(dirUserPerms + dirGroupPerms + dirOtherPerms);
    }
    else if(otherMask==1)
    {
      fileOtherBits=6;
      fileOther.setText(Byte.toString(fileOtherBits));
      dirOtherBits=6;
      dirOther.setText(Byte.toString(dirOtherBits));
      fileOtherPerms="rw-";
      fsBits.setText(fileUserPerms + fileGroupPerms + fileOtherPerms);
      dirOtherPerms="rw-";
      dsBits.setText(dirUserPerms + dirGroupPerms + dirOtherPerms);
    }
    else if(otherMask==2)
    {
      fileOtherBits=4;
      fileOther.setText(Byte.toString(fileOtherBits));
      dirOtherBits=5;
      dirOther.setText(Byte.toString(dirOtherBits));
      fileOtherPerms="r--";
      fsBits.setText(fileUserPerms + fileGroupPerms + fileOtherPerms);
      dirOtherPerms="r-x";
      dsBits.setText(dirUserPerms + dirGroupPerms + dirOtherPerms);
    }
    else if(otherMask==3)
    {
      fileOtherBits=4;
      fileOther.setText(Byte.toString(fileOtherBits));
      dirOtherBits=4;
      dirOther.setText(Byte.toString(dirOtherBits));
      fileOtherPerms="r--";
      fsBits.setText(fileUserPerms + fileGroupPerms + fileOtherPerms);
      dirOtherPerms="r--";
      dsBits.setText(dirUserPerms + dirGroupPerms + dirOtherPerms);
    }
    else if(otherMask==4)
    {
      fileOtherBits=2;
      fileOther.setText(Byte.toString(fileOtherBits));
      dirOtherBits=3;
      dirOther.setText(Byte.toString(dirOtherBits));
      fileOtherPerms="-w-";
      fsBits.setText(fileUserPerms + fileGroupPerms + fileOtherPerms);
      dirOtherPerms="-wx";
      dsBits.setText(dirUserPerms + dirGroupPerms + dirOtherPerms);
    }
    else if(otherMask==5)
    {
      fileOtherBits=2;
      fileOther.setText(Byte.toString(fileOtherBits));
      dirOtherBits=2;
      dirOther.setText(Byte.toString(dirOtherBits));
      fileOtherPerms="-w-";
      fsBits.setText(fileUserPerms + fileGroupPerms + fileOtherPerms);
      dirOtherPerms="-w-";
      dsBits.setText(dirUserPerms + dirGroupPerms + dirOtherPerms);
    }
    else if(otherMask==6)
    {
      fileOtherBits=0;
      fileOther.setText(Byte.toString(fileOtherBits));
      dirOtherBits=1;
      dirOther.setText(Byte.toString(dirOtherBits));
      fileOtherPerms="---";
      fsBits.setText(fileUserPerms + fileGroupPerms + fileOtherPerms);
      dirOtherPerms="--x";
      dsBits.setText(dirUserPerms + dirGroupPerms + dirOtherPerms);
    }
    else if(otherMask==7)
    {
      fileOtherBits=0;
      fileOther.setText(Byte.toString(fileOtherBits));
      dirOtherBits=0;
      dirOther.setText(Byte.toString(dirOtherBits));
      fileOtherPerms="---";
      fsBits.setText(fileUserPerms + fileGroupPerms + fileOtherPerms);
      dirOtherPerms="---";
      dsBits.setText(dirUserPerms + dirGroupPerms + dirOtherPerms);
    }
  }

  private static void clearValues()
  {
    userMask=0;
    groupMask=0;
    otherMask=0;
    userBits.setText("-");
    groupBits.setText("-");
    otherBits.setText("-");
    fileUserBits=0;
    fileUser.setText(Byte.toString(fileUserBits));
    fileGroupBits=0;
    fileGroup.setText(Byte.toString(fileGroupBits));
    fileOtherBits=0;
    fileOther.setText(Byte.toString(fileOtherBits));
    dirUserBits=0;
    dirUser.setText(Byte.toString(dirUserBits));
    dirGroupBits=0;
    dirGroup.setText(Byte.toString(dirGroupBits));
    dirOtherBits=0;
    dirOther.setText(Byte.toString(dirOtherBits));
    fsBits.setText("----------");
    dsBits.setText("d---------");
    initialRun=true;
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.main_menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item)
  {
    switch(item.getItemId())
    {
      case R.id.about:
        showAbout();
        return true;
      case R.id.quit:
        System.exit(0);
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  private final void showAbout()
  {
    final SpannableString aboutInfo=new SpannableString("    Umask Calculator\n    Version: 1.0\n    Author: Lloyd Dilley\n    lloyd@dilley.me\n    http://www.dilley.me/");
	final AlertDialog.Builder aboutDialog=new AlertDialog.Builder(this);
	final TextView tv=new TextView(this);
	Linkify.addLinks(aboutInfo, Linkify.ALL);
	tv.setMovementMethod(LinkMovementMethod.getInstance());
    tv.setText(aboutInfo);
    tv.setTextColor(Color.WHITE);
    tv.setLinkTextColor(Color.RED);
    aboutDialog.setTitle("About");
    aboutDialog.setIcon(android.R.drawable.ic_dialog_info);
    aboutDialog.setPositiveButton("OK", null);
    aboutDialog.setCancelable(true);
    aboutDialog.setView(tv);
    aboutDialog.create().show();
  }
}
