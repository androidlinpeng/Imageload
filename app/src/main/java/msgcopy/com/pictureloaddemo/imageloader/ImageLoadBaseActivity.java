package msgcopy.com.pictureloaddemo.imageloader;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.nostra13.universalimageloader.core.ImageLoader;

import msgcopy.com.pictureloaddemo.R;

public class ImageLoadBaseActivity extends AppCompatActivity {

    protected ImageLoader imageLoader = ImageLoader.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_clear_memory_cache:
                imageLoader.clearMemoryCache();
                return true;
            case R.id.item_clear_disc_cache:
                imageLoader.clearDiscCache();
                return true;
            default:
                return false;
        }
    }
}
