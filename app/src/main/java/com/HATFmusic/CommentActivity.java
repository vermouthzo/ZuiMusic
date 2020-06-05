package com.HATFmusic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Random;

public class CommentActivity extends AppCompatActivity {
    private ImageView back_play;
    private ConstraintLayout back;
    private List<Comment> hotComments;
    private Song currentSong;
    private final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private Button sendButton;
    private SongLab lab = SongLab.getInstance();

    public final static String TAG = "Zui";
    public final static int MSG_HOT_COMMENTS = 1;
    public final static int MSG_ADD_COMMENT = 2;
    public final static int MSG_NET_FAILURE = -1;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case MSG_HOT_COMMENTS://显示热门评论
                    hotComments = (List<Comment>) msg.obj;
                    updateUI();
                    break;
                case MSG_ADD_COMMENT://评论成功了，提示一下用户
                    Toast.makeText(CommentActivity.this, "感谢您的留言！",
                            Toast.LENGTH_LONG)
                            .show();
                    break;
                case MSG_NET_FAILURE:  //评论失败了，提示一下用户
                    Toast.makeText(CommentActivity.this, "评论失败，请稍候再试。",
                            Toast.LENGTH_LONG)
                            .show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        back_play = findViewById(R.id.back_play);
        back = findViewById(R.id.back);

        back_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CommentActivity.this, "", Toast.LENGTH_LONG)
                        .show();
                Intent intent = new Intent(CommentActivity.this, PlayActivity.class);
                CommentActivity.this.startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CommentActivity.this, "", Toast.LENGTH_LONG)
                        .show();
                Intent intent = new Intent(CommentActivity.this, PlayActivity.class);
                CommentActivity.this.startActivity(intent);
            }
        });

        Serializable s = getIntent().getSerializableExtra("song");

        Log.d(TAG, "取得的当前频道对象是：" + s);
        if (s != null && s instanceof Song) {
            currentSong = (Song) s;
            updateUI();
            sendButton = findViewById(R.id.send);

            sendButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditText t = CommentActivity.this.findViewById(R.id.message);
                    Comment c = new Comment();
                    c.setAuthor("MyApp");
                    //改进，随机点赞数(0至100)
                    Random random = new Random();
                    c.setStar(random.nextInt(100));
                    c.setContent(t.getText().toString());
                    lab.addComment(currentSong.getId(), c, handler);
                }
            });
        }
    }

    private void updateUI() {
        //显示热门评论
        if (hotComments != null && hotComments.size() > 0) {
            Comment c1 = hotComments.get(0);
            TextView username1, date1, content1, score1;
            username1 = findViewById(R.id.username1);
            date1 = findViewById(R.id.date1);
            content1 = findViewById(R.id.content1);
            score1 = findViewById(R.id.score1);
            username1.setText(c1.getAuthor());
            date1.setText(dateFormat.format(c1.getDt()));
            content1.setText(c1.getContent());
            score1.setText(c1.getStar() + "");
        }
        if (hotComments != null && hotComments.size() > 1) {
            Comment c2 = hotComments.get(1);
            TextView username2, date2, content2, score2;
            username2 = findViewById(R.id.username2);
            date2 = findViewById(R.id.date2);
            content2 = findViewById(R.id.content2);
            score2 = findViewById(R.id.score2);
            username2.setText(c2.getAuthor());
            date2.setText(dateFormat.format(c2.getDt()));
            content2.setText(c2.getContent());
            score2.setText(c2.getStar() + "");
        }
    }
}
