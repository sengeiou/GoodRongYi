package com.lichi.goodrongyi.component;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blog.www.guideview.Component;
import com.lichi.goodrongyi.R;

/**
 * Created by binIoter on 16/6/17.
 */
public class SimpleComponent implements Component {
  String title;

  public SimpleComponent(String title){
    this.title=title;
  }
  @Override
  public View getView(LayoutInflater inflater) {

    LinearLayout ll = (LinearLayout) inflater.inflate(R.layout.layer_frends, null);
    TextView textView= (TextView) ll.findViewById(R.id.title);
    textView.setText(title);
 /*   ll.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Toast.makeText(view.getContext(), "引导层被点击了", Toast.LENGTH_SHORT).show();
      }
    });*/
    return ll;
  }

  @Override
  public int getAnchor() {
    return Component.ANCHOR_BOTTOM;
  }

  @Override
  public int getFitPosition() {
    return Component.FIT_END;
  }

  @Override
  public int getXOffset() {
    return 0;
  }

  @Override
  public int getYOffset() {
    return 10;
  }
}
