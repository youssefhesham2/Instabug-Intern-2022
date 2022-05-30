package com.example.instabugintern2022.mainactivity

class MainPresenter {
    private var view: MainView? = null
    fun onAttached(view: MainView) {
        this.view = view
    }

    fun onClickAddNewHeaderBtn() {
        view?.addNewHeaderField()
    }

    fun onSpinnerSelectedItem(position: Int) {
        if (position == 0) view?.goneBodyViewVisibility()
        else view?.visibleBodyViewVisibility()
    }
}