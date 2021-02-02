package com.horganice.helpcrunch;
// Cordova-required packages

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.helpcrunch.library.core.HelpCrunch;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HelpcrunchPlugin extends CordovaPlugin {
  private enum Action {
    initialize {
      @Override
      void performAction(JSONArray args, CallbackContext callbackContext, CordovaInterface cordova) {
        String ORGANISATION = args.optString(0);
        Integer APP_ID = args.optInt(1);
        String SECRET = args.optString(2);
        Log.v("TEST",ORGANISATION);
        Log.v("TEST", String.valueOf(APP_ID));
        Log.v("TEST",SECRET);
        HelpCrunch.initialize(ORGANISATION, APP_ID, SECRET);
        callbackContext.success();
      }
    },
    showChatScreen {
      @Override
      void performAction(JSONArray args, CallbackContext callbackContext, CordovaInterface cordova) {
        Context context = cordova.getActivity().getApplicationContext();
        HelpCrunch.showChatScreen(context);
        callbackContext.success();
      }
    },
    getUnreadChatsCount {
      @Override 
      void performAction(JSONArray args, CallbackContext callbackContext, CordovaInterface cordova) {
        HelpCrunch.getUnreadChatsCount(new Callback<Integer>() {
            @Override
            public void onSuccess(Integer result) {
               callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, result));
            }

            @Override
            public void onError(@NotNull String message) {
               callbackContext.error(message);
            }
        });
      }
    },
    unknown {
      @Override
      void performAction(JSONArray args, CallbackContext callbackContext, CordovaInterface cordova) {
        callbackContext.error("[Helpcrunch-Cordova] ERROR: Undefined function");
      }
    };

    abstract void performAction(JSONArray args, CallbackContext callbackContext, CordovaInterface cordova);

    public static Action fromString(String actionAsString) {
      Action action = unknown;

      try {
        action = valueOf(actionAsString);
      } catch (NullPointerException ignored) {
      } catch (IllegalArgumentException ignored) {
      }

      return action;
    }
  }


  public boolean execute(String actionString, final JSONArray args, final CallbackContext callbackContext) {
    final Action action = Action.fromString(actionString);

    cordova.getActivity().runOnUiThread(new Runnable() {
      @Override
      public void run() {
        action.performAction(args, callbackContext, HelpcrunchPlugin.this.cordova);
      }
    });
    return action != Action.unknown;
  }

}
