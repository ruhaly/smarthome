package android.support.v4.os;

import android.os.Parcelable;

public class ParcelableCompatCreatorHoneycombMR2Stub {
    static <T> Parcelable.Creator<T> instantiate(
            ParcelableCompatCreatorCallbacks<T> callbacks) {
        return new ParcelableCompatCreatorHoneycombMR2<T>(callbacks);
    }
}
