package demo.aws.backend.batch.core;

public final class Constants {
    private Constants() {}

    public static final String PROP_JOB_ID = "batch";

    public static final String PROP_JOB_ID_WITH_HOLDER = "${batch}";

    public static final String TIMEZONE_JP = "JST";

    public static final String TIMEZONE_UTC = "UTC";

    // データ登録の場合
    public static final String INSERT_BEFORE = "DB名：{}、コレクション名：{}、登録前の件数：{}件";
    public static final String INSERT_AFTER = "DB名：{}、コレクション名：{}、登録後の件数：{}件";
    public static final String INSERT_SUCCESS = "リカバリ成功、問題なし";
    public static final String INSERT_ERROR = "リカバリ失敗、問題あり";

    // データ更新でログに出力してほしい
    public static final String UPDATE_BEFORE = "DB名：{}、コレクション名：{}、更新前の件数：{}件";
    public static final String UPDATE_AFTER = "DB名：{}、コレクション名：{}、更新後の件数：{}件";
    public static final String UPDATE_SUCCESS = "リカバリ成功、更新件数が一致";
    public static final String UPDATE_ERROR = "リカバリ失敗、更新件数が不一致";

    // データ削除の場合
    public static final String DELETE_BEFORE = "DB名：{}、コレクション名：{}、削除前の件数：{}件";
    public static final String DELETE_AFTER = "DB名：{}、コレクション名：{}、削除後の件数：{}件";
    public static final String DELETE_SUCCESS = "リカバリ成功、問題なし";
    public static final String DELETE_ERROR = "リカバリ失敗、削除失敗";
    public static final String DELETE_ERROR_2 = "リカバリ失敗、データ不備あり";
}
