package satel.adsviewer;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * Created by satel on 3/28/2018.
 */

@Database(entities = {Ad_Block.class}, version = 1)
public abstract class adDatabase extends RoomDatabase {

    private static adDatabase INSTANCE;

    public abstract AdDao AdDao();
}
