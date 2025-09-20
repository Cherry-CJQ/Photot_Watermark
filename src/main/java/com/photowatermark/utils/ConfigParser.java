package com.photowatermark.utils;

/**
 * 命令行参数解析器
 */
public class ConfigParser {

    /**
     * 配置类，包含所有水印参数
     */
    public static class Config {

        private String inputPath;
        private int fontSize = 24;
        private String color = "FFFFFF";
        private String position = "bottom-right";

        public Config(String inputPath) {
            this.inputPath = inputPath;
        }

        public String getInputPath() {
            return inputPath;
        }

        public int getFontSize() {
            return fontSize;
        }

        public void setFontSize(int fontSize) {
            this.fontSize = fontSize;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        @Override
        public String toString() {
            return "Config{"
                    + "inputPath='" + inputPath + '\''
                    + ", fontSize=" + fontSize
                    + ", color='" + color + '\''
                    + ", position='" + position + '\''
                    + '}';
        }
    }

    /**
     * 解析命令行参数
     */
    public static Config parseArgs(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("必须提供输入路径");
        }

        Config config = new Config(args[0]);

        for (int i = 1; i < args.length; i++) {
            String arg = args[i];

            switch (arg) {
                case "-fontSize":
                    if (i + 1 < args.length) {
                        try {
                            config.setFontSize(Integer.parseInt(args[++i]));
                        } catch (NumberFormatException e) {
                            throw new IllegalArgumentException("字体大小必须是整数");
                        }
                    } else {
                        throw new IllegalArgumentException("缺少字体大小参数");
                    }
                    break;

                case "-color":
                    if (i + 1 < args.length) {
                        String color = args[++i];
                        if (!isValidHexColor(color)) {
                            throw new IllegalArgumentException("颜色必须是6位十六进制格式");
                        }
                        config.setColor(color.toUpperCase());
                    } else {
                        throw new IllegalArgumentException("缺少颜色参数");
                    }
                    break;

                case "-position":
                    if (i + 1 < args.length) {
                        String position = args[++i].toLowerCase();
                        if (!isValidPosition(position)) {
                            throw new IllegalArgumentException("位置必须是: top-left, center, bottom-right");
                        }
                        config.setPosition(position);
                    } else {
                        throw new IllegalArgumentException("缺少位置参数");
                    }
                    break;

                default:
                    throw new IllegalArgumentException("未知参数: " + arg);
            }
        }

        return config;
    }

    /**
     * 验证十六进制颜色格式
     */
    private static boolean isValidHexColor(String color) {
        return color.matches("^[0-9A-Fa-f]{6}$");
    }

    /**
     * 验证水印位置格式
     */
    private static boolean isValidPosition(String position) {
        return position.equals("top-left")
                || position.equals("center")
                || position.equals("bottom-right");
    }
}
