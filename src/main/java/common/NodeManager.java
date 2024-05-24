package common;

import memory.CacheDB;
import memory.Database;

public final class NodeManager {
    public static Database DEFAULT_DB = new CacheDB();
    public static MetaData metaData = new MetaData();
}
