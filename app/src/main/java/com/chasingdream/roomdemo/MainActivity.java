package com.chasingdream.roomdemo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.chasingdream.room.User;
import com.chasingdream.room.UserDatabase;
import com.chasingdream.room.observer.RxCompositeDisposable;
import com.chasingdream.room.observer.RxSingleObserver;

import java.util.List;

import io.reactivex.functions.Action;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    RxCompositeDisposable rxCompositeDisposable = RxCompositeDisposable.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void addData(View view) {
        //添加数据
        User user = new User("小明", 50);
        rxCompositeDisposable.addDisposable(UserDatabase.getInstance(this).getUserDao().insert(user), new io.reactivex.functions.Action() {
            @Override
            public void run() throws Exception {
                Toast.makeText(MainActivity.this, "小明我添加了好几条了", Toast.LENGTH_SHORT).show();
            }
        });

        Log.w(TAG, "===>addData");
    }

    public void deleteData(View view) {
        //删除数据
        Log.w(TAG, "===>deleteData");
        rxCompositeDisposable.addDisposable(UserDatabase.getInstance(this).getUserDao().getAllUsers(), new RxSingleObserver<List<User>>() {
            @Override
            public void onSuccess(List<User> users) {
                if (users != null && users.size() > 0) {
                    rxCompositeDisposable.addDisposable(UserDatabase.getInstance(MainActivity.this).getUserDao().delete(users.get(0)), new Action() {
                        @Override
                        public void run() throws Exception {
                            Toast.makeText(MainActivity.this, "数据我都删除了", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        });

    }

    public void selectData(View view) {
        //TODO 有空封装
        rxCompositeDisposable.addDisposable(UserDatabase.getInstance(this).getUserDao().getAllUsers(), new RxSingleObserver<List<User>>() {
            @Override
            public void onSuccess(List<User> users) {
                Log.w(TAG, "===>selectData" + users.toString());
                Toast.makeText(MainActivity.this, "查询有几个数据" + users.size(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        rxCompositeDisposable.clear();

    }

    public void selectOneDataById(View view) {
        rxCompositeDisposable.addDisposable(UserDatabase.getInstance(this).getUserDao().getUserById(4), new RxSingleObserver<User>() {
            @Override
            public void onSuccess(User user) {
                Toast.makeText(MainActivity.this, "查询到了", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(MainActivity.this, "未查询到", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateData(View view) {
        Log.w(TAG, "===>updateData" + Thread.currentThread());
        rxCompositeDisposable.addAsyncDisposable(UserDatabase.getInstance(this).getUserDao().getUserById(4), new RxSingleObserver<User>() {
            @Override
            public void onSuccess(User user) {
                Log.w(TAG, "===>当前线程" + Thread.currentThread() + user.toString());
                user.setAge(90);
                /**
                 rxCompositeDisposable.addDisposable(UserDatabase.getInstance(MainActivity.this).getUserDao().update(user), new Action() {
                @Override public void run() throws Exception {

                }
                });
                 */
                UserDatabase.getInstance(MainActivity.this).getUserDao().updateNoRx(user);
            }

            @Override
            public void onError(Throwable e) {
                Log.w(TAG, "===>当前线程" + Thread.currentThread());
            }
        });
    }
}
