package msgcopy.com.pictureloaddemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import msgcopy.com.pictureloaddemo.fresco.FrescoActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.fresco:
                startActivity(new Intent(MainActivity.this,FrescoActivity.class));
                break;
            default:
                break;
        }
    }
}
