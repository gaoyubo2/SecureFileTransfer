package gyb.securefiletransfer.common.utils;

public class SecureUtil {
    // 将密码哈希存储
    public static String hashPassword(String plainPassword) {
        return cn.hutool.crypto.SecureUtil.md5(plainPassword); // 使用MD5哈希算法示例
    }

    // 验证密码
    public static boolean verifyPassword(String plainPassword, String hashedPassword) {
        String hashedInput = cn.hutool.crypto.SecureUtil.md5(plainPassword); // 对输入的密码进行哈希
        return hashedInput.equals(hashedPassword); // 比较哈希后的值
    }

}
