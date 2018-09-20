package maddie.dolo.ui;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.Button;
import android.widget.RemoteViews;

import maddie.dolo.DoloUtil;
import maddie.dolo.R;

/**
 * Implementation of App Widget functionality.
 */
public class DoloWidget extends AppWidgetProvider {


    private Button lyftButton;

    private Button uberButton;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.dolo_widget);

        views.setOnClickFillInIntent(R.id.lyft_widget_button, GetThereActivity.getPlayStoreIntent(DoloUtil.LYFT_INTENT_URI));
        views.setOnClickFillInIntent(R.id.uber_widget_button, GetThereActivity.getPlayStoreIntent(DoloUtil.UBER_INTENT_URI));

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

