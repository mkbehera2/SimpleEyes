package com.jennifer.andy.simplemusic.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import butterknife.ButterKnife
import com.jennifer.andy.simplemusic.R
import com.jennifer.andy.simplemusic.manager.BaseAppManager
import me.yokeyword.fragmentation.SupportActivity


/**
 * Author:  andy.xwt
 * Date:    2017/8/10 22:37
 * Description:
 */

abstract class BaseAppCompatActivity : SupportActivity() {

    /**
     * 上下文对象
     */
    protected var mContext: Context? = null
    protected var TAT_LOG: String? = null

    /**
     * 跳转到其他Activity启动或者退出的模式
     */
    enum class TransitionMode {
        LEFT, RIGHT, TOP, BOTTOM, SCALE, FADE
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        overrideTransitionAnimation()
        val extras = intent.extras
        if (extras != null) {
            getBundleExtras(extras)
        }
        //获取上下文并设置log标记
        TAT_LOG = this.javaClass.simpleName
        mContext = this
        BaseAppManager.getInstance().addActivity(this)

        //添加相应的布局
        if (getContentViewLayoutId() != 0) {
            setContentView(getContentViewLayoutId())
        } else {
            throw  IllegalArgumentException("You must return layout id")
        }

    }

    /**
     *  获取bundle 中的数据
     */
    abstract fun getBundleExtras(extras: Bundle)

    /**
     *  获取当前多状态根视图
     */
    abstract fun getTargetView(): View?

    /**
     * 是否有切换动画
     */
    abstract fun toggleOverridePendingTransition(): Boolean

    /**
     * 获得切换动画的模式
     */
    abstract fun getOverridePendingTransition(): TransitionMode

    /**
     * 获取当前布局id
     */
    abstract fun getContentViewLayoutId(): Int

    /**
     * 设置activity进入动画
     */
    private fun overrideTransitionAnimation() {
        if (toggleOverridePendingTransition()) {
            when (getOverridePendingTransition()) {
                TransitionMode.TOP -> overridePendingTransition(R.anim.top_in, R.anim.no_anim)
                TransitionMode.BOTTOM -> overridePendingTransition(R.anim.bottom_in, R.anim.no_anim)
                TransitionMode.LEFT -> overridePendingTransition(R.anim.left_in, R.anim.no_anim)
                TransitionMode.RIGHT -> overridePendingTransition(R.anim.right_in, R.anim.no_anim)
                TransitionMode.FADE -> overridePendingTransition(R.anim.fade_in, R.anim.no_anim)
                TransitionMode.SCALE -> overridePendingTransition(R.anim.scale_in, R.anim.no_anim)
            }
        }
    }

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        ButterKnife.bind(this)
        if (null != getTargetView()) {
        }
    }


    override fun onDestroy() {
        BaseAppManager.getInstance().removeActivity(this)
        super.onDestroy()
    }

}