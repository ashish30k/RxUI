package com.ashish.android.rxui;

import io.reactivex.Observable;

public class ValidationUtils {
    public static Observable<Boolean> and(Observable<Boolean> a, Observable<Boolean> b) {
        return Observable.combineLatest(a, b, (valueA, valueB) -> valueA && valueB);
    }

    public static Observable<Boolean> or(Observable<Boolean> a, Observable<Boolean> b) {
        return Observable.combineLatest(a, b, (valueA, valueB) -> valueA || valueB);
    }

}
