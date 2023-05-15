package vn.com.hust.common;

public class AppPath {
    public static final String ACTION_EXPORT_FILE = "/hust/exportFile";

    public static final class REQUEST_MAPPING {
        // BankAccount START
        public static final String GET_ALL_BANK_ACCOUNT_BY_DEPARTMENT = "/get-by-department";

        public static final String ON_SEARCH_BANK_ACCOUNT = "/on-search";
        // BankAccount END

        // Catalog START
        public static final String GET_ALL_CATALOG_BY_ID = "/get-all-by-id";

        public static final String GET_TREE_CATALOG = "/get-tree-catalog";
        // Catalog END
    }
}
