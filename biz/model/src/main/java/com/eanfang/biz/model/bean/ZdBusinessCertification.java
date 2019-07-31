package com.eanfang.biz.model.bean;

public class ZdBusinessCertification {

    /**
     * log_id : 2403126602713063595
     * direction : 0
     * words_result_num : 11
     * words_result : {"注册资本":{"location":{"width":0,"top":0,"height":0,"left":0},"words":"无"},"社会信用代码":{"location":{"width":0,"top":0,"height":0,"left":0},"words":"无"},"单位名称":{"location":{"width":0,"top":0,"height":0,"left":0},"words":"无"},"法人":{"location":{"width":62,"top":198,"height":29,"left":216},"words":"翟华波"},"证件编号":{"location":{"width":0,"top":0,"height":0,"left":0},"words":"无"},"组成形式":{"location":{"width":0,"top":0,"height":0,"left":0},"words":"无"},"成立日期":{"location":{"width":0,"top":0,"height":0,"left":0},"words":"无"},"地址":{"location":{"width":312,"top":136,"height":45,"left":216},"words":"宁波市州区山山镇明光北路1166号"},"经营范围":{"location":{"width":388,"top":348,"height":250,"left":200},"words":"一市西量到形能雅而量批进记机英"},"类型":{"location":{"width":195,"top":100,"height":28,"left":214},"words":"股份有限公司(l上"},"有效期":{"location":{"width":249,"top":319,"height":27,"left":221},"words":"长期"}}
     */

    private long log_id;
    private int direction;
    private int words_result_num;
    private WordsResultBean words_result;

    public long getLog_id() {
        return log_id;
    }

    public void setLog_id(long log_id) {
        this.log_id = log_id;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getWords_result_num() {
        return words_result_num;
    }

    public void setWords_result_num(int words_result_num) {
        this.words_result_num = words_result_num;
    }

    public WordsResultBean getWords_result() {
        return words_result;
    }

    public void setWords_result(WordsResultBean words_result) {
        this.words_result = words_result;
    }

    public static class WordsResultBean {
        @Override
        public String toString() {
            return "WordsResultBean{" +
                    "注册资本=" + 注册资本 +
                    ", 社会信用代码=" + 社会信用代码 +
                    ", 单位名称=" + 单位名称 +
                    ", 法人=" + 法人 +
                    ", 证件编号=" + 证件编号 +
                    ", 组成形式=" + 组成形式 +
                    ", 成立日期=" + 成立日期 +
                    ", 地址=" + 地址 +
                    ", 经营范围=" + 经营范围 +
                    ", 类型=" + 类型 +
                    ", 有效期=" + 有效期 +
                    '}';
        }

        /**
         * 注册资本 : {"location":{"width":0,"top":0,"height":0,"left":0},"words":"无"}
         * 社会信用代码 : {"location":{"width":0,"top":0,"height":0,"left":0},"words":"无"}
         * 单位名称 : {"location":{"width":0,"top":0,"height":0,"left":0},"words":"无"}
         * 法人 : {"location":{"width":62,"top":198,"height":29,"left":216},"words":"翟华波"}
         * 证件编号 : {"location":{"width":0,"top":0,"height":0,"left":0},"words":"无"}
         * 组成形式 : {"location":{"width":0,"top":0,"height":0,"left":0},"words":"无"}
         * 成立日期 : {"location":{"width":0,"top":0,"height":0,"left":0},"words":"无"}
         * 地址 : {"location":{"width":312,"top":136,"height":45,"left":216},"words":"宁波市州区山山镇明光北路1166号"}
         * 经营范围 : {"location":{"width":388,"top":348,"height":250,"left":200},"words":"一市西量到形能雅而量批进记机英"}
         * 类型 : {"location":{"width":195,"top":100,"height":28,"left":214},"words":"股份有限公司(l上"}
         * 有效期 : {"location":{"width":249,"top":319,"height":27,"left":221},"words":"长期"}
         */

        private 注册资本Bean 注册资本;
        private 社会信用代码Bean 社会信用代码;
        private 单位名称Bean 单位名称;
        private 法人Bean 法人;
        private 证件编号Bean 证件编号;
        private 组成形式Bean 组成形式;
        private 成立日期Bean 成立日期;
        private 地址Bean 地址;
        private 经营范围Bean 经营范围;
        private 类型Bean 类型;
        private 有效期Bean 有效期;

        public 注册资本Bean get注册资本() {
            return 注册资本;
        }

        public void set注册资本(注册资本Bean 注册资本) {
            this.注册资本 = 注册资本;
        }

        public 社会信用代码Bean get社会信用代码() {
            return 社会信用代码;
        }

        public void set社会信用代码(社会信用代码Bean 社会信用代码) {
            this.社会信用代码 = 社会信用代码;
        }

        public 单位名称Bean get单位名称() {
            return 单位名称;
        }

        public void set单位名称(单位名称Bean 单位名称) {
            this.单位名称 = 单位名称;
        }

        public 法人Bean get法人() {
            return 法人;
        }

        public void set法人(法人Bean 法人) {
            this.法人 = 法人;
        }

        public 证件编号Bean get证件编号() {
            return 证件编号;
        }

        public void set证件编号(证件编号Bean 证件编号) {
            this.证件编号 = 证件编号;
        }

        public 组成形式Bean get组成形式() {
            return 组成形式;
        }

        public void set组成形式(组成形式Bean 组成形式) {
            this.组成形式 = 组成形式;
        }

        public 成立日期Bean get成立日期() {
            return 成立日期;
        }

        public void set成立日期(成立日期Bean 成立日期) {
            this.成立日期 = 成立日期;
        }

        public 地址Bean get地址() {
            return 地址;
        }

        public void set地址(地址Bean 地址) {
            this.地址 = 地址;
        }

        public 经营范围Bean get经营范围() {
            return 经营范围;
        }

        public void set经营范围(经营范围Bean 经营范围) {
            this.经营范围 = 经营范围;
        }

        public 类型Bean get类型() {
            return 类型;
        }

        public void set类型(类型Bean 类型) {
            this.类型 = 类型;
        }

        public 有效期Bean get有效期() {
            return 有效期;
        }

        public void set有效期(有效期Bean 有效期) {
            this.有效期 = 有效期;
        }

        public static class 注册资本Bean {
            @Override
            public String toString() {
                return "注册资本Bean{" +
                        "location=" + location +
                        ", words='" + words + '\'' +
                        '}';
            }

            /**
             * location : {"width":0,"top":0,"height":0,"left":0}
             * words : 无
             */

            private LocationBean location;
            private String words;

            public LocationBean getLocation() {
                return location;
            }

            public void setLocation(LocationBean location) {
                this.location = location;
            }

            public String getWords() {
                return words;
            }

            public void setWords(String words) {
                this.words = words;
            }

            public static class LocationBean {
                /**
                 * width : 0
                 * top : 0
                 * height : 0
                 * left : 0
                 */

                private int width;
                private int top;
                private int height;
                private int left;

                public int getWidth() {
                    return width;
                }

                public void setWidth(int width) {
                    this.width = width;
                }

                public int getTop() {
                    return top;
                }

                public void setTop(int top) {
                    this.top = top;
                }

                public int getHeight() {
                    return height;
                }

                public void setHeight(int height) {
                    this.height = height;
                }

                public int getLeft() {
                    return left;
                }

                public void setLeft(int left) {
                    this.left = left;
                }
            }
        }

        public static class 社会信用代码Bean {
            /**
             * location : {"width":0,"top":0,"height":0,"left":0}
             * words : 无
             */

            private LocationBeanX location;
            private String words;

            public LocationBeanX getLocation() {
                return location;
            }

            public void setLocation(LocationBeanX location) {
                this.location = location;
            }

            public String getWords() {
                return words;
            }

            public void setWords(String words) {
                this.words = words;
            }

            public static class LocationBeanX {
                /**
                 * width : 0
                 * top : 0
                 * height : 0
                 * left : 0
                 */

                private int width;
                private int top;
                private int height;
                private int left;

                public int getWidth() {
                    return width;
                }

                public void setWidth(int width) {
                    this.width = width;
                }

                public int getTop() {
                    return top;
                }

                public void setTop(int top) {
                    this.top = top;
                }

                public int getHeight() {
                    return height;
                }

                public void setHeight(int height) {
                    this.height = height;
                }

                public int getLeft() {
                    return left;
                }

                public void setLeft(int left) {
                    this.left = left;
                }
            }
        }

        public static class 单位名称Bean {
            /**
             * location : {"width":0,"top":0,"height":0,"left":0}
             * words : 无
             */

            private LocationBeanXX location;
            private String words;

            public LocationBeanXX getLocation() {
                return location;
            }

            public void setLocation(LocationBeanXX location) {
                this.location = location;
            }

            public String getWords() {
                return words;
            }

            public void setWords(String words) {
                this.words = words;
            }

            public static class LocationBeanXX {
                /**
                 * width : 0
                 * top : 0
                 * height : 0
                 * left : 0
                 */

                private int width;
                private int top;
                private int height;
                private int left;

                public int getWidth() {
                    return width;
                }

                public void setWidth(int width) {
                    this.width = width;
                }

                public int getTop() {
                    return top;
                }

                public void setTop(int top) {
                    this.top = top;
                }

                public int getHeight() {
                    return height;
                }

                public void setHeight(int height) {
                    this.height = height;
                }

                public int getLeft() {
                    return left;
                }

                public void setLeft(int left) {
                    this.left = left;
                }
            }
        }

        public static class 法人Bean {
            /**
             * location : {"width":62,"top":198,"height":29,"left":216}
             * words : 翟华波
             */

            private LocationBeanXXX location;
            private String words;

            public LocationBeanXXX getLocation() {
                return location;
            }

            public void setLocation(LocationBeanXXX location) {
                this.location = location;
            }

            public String getWords() {
                return words;
            }

            public void setWords(String words) {
                this.words = words;
            }

            public static class LocationBeanXXX {
                /**
                 * width : 62
                 * top : 198
                 * height : 29
                 * left : 216
                 */

                private int width;
                private int top;
                private int height;
                private int left;

                public int getWidth() {
                    return width;
                }

                public void setWidth(int width) {
                    this.width = width;
                }

                public int getTop() {
                    return top;
                }

                public void setTop(int top) {
                    this.top = top;
                }

                public int getHeight() {
                    return height;
                }

                public void setHeight(int height) {
                    this.height = height;
                }

                public int getLeft() {
                    return left;
                }

                public void setLeft(int left) {
                    this.left = left;
                }
            }
        }

        public static class 证件编号Bean {
            /**
             * location : {"width":0,"top":0,"height":0,"left":0}
             * words : 无
             */

            private LocationBeanXXXX location;
            private String words;

            public LocationBeanXXXX getLocation() {
                return location;
            }

            public void setLocation(LocationBeanXXXX location) {
                this.location = location;
            }

            public String getWords() {
                return words;
            }

            public void setWords(String words) {
                this.words = words;
            }

            public static class LocationBeanXXXX {
                /**
                 * width : 0
                 * top : 0
                 * height : 0
                 * left : 0
                 */

                private int width;
                private int top;
                private int height;
                private int left;

                public int getWidth() {
                    return width;
                }

                public void setWidth(int width) {
                    this.width = width;
                }

                public int getTop() {
                    return top;
                }

                public void setTop(int top) {
                    this.top = top;
                }

                public int getHeight() {
                    return height;
                }

                public void setHeight(int height) {
                    this.height = height;
                }

                public int getLeft() {
                    return left;
                }

                public void setLeft(int left) {
                    this.left = left;
                }
            }
        }

        public static class 组成形式Bean {
            /**
             * location : {"width":0,"top":0,"height":0,"left":0}
             * words : 无
             */

            private LocationBeanXXXXX location;
            private String words;

            public LocationBeanXXXXX getLocation() {
                return location;
            }

            public void setLocation(LocationBeanXXXXX location) {
                this.location = location;
            }

            public String getWords() {
                return words;
            }

            public void setWords(String words) {
                this.words = words;
            }

            public static class LocationBeanXXXXX {
                /**
                 * width : 0
                 * top : 0
                 * height : 0
                 * left : 0
                 */

                private int width;
                private int top;
                private int height;
                private int left;

                public int getWidth() {
                    return width;
                }

                public void setWidth(int width) {
                    this.width = width;
                }

                public int getTop() {
                    return top;
                }

                public void setTop(int top) {
                    this.top = top;
                }

                public int getHeight() {
                    return height;
                }

                public void setHeight(int height) {
                    this.height = height;
                }

                public int getLeft() {
                    return left;
                }

                public void setLeft(int left) {
                    this.left = left;
                }
            }
        }

        public static class 成立日期Bean {
            /**
             * location : {"width":0,"top":0,"height":0,"left":0}
             * words : 无
             */

            private LocationBeanXXXXXX location;
            private String words;

            public LocationBeanXXXXXX getLocation() {
                return location;
            }

            public void setLocation(LocationBeanXXXXXX location) {
                this.location = location;
            }

            public String getWords() {
                return words;
            }

            public void setWords(String words) {
                this.words = words;
            }

            public static class LocationBeanXXXXXX {
                /**
                 * width : 0
                 * top : 0
                 * height : 0
                 * left : 0
                 */

                private int width;
                private int top;
                private int height;
                private int left;

                public int getWidth() {
                    return width;
                }

                public void setWidth(int width) {
                    this.width = width;
                }

                public int getTop() {
                    return top;
                }

                public void setTop(int top) {
                    this.top = top;
                }

                public int getHeight() {
                    return height;
                }

                public void setHeight(int height) {
                    this.height = height;
                }

                public int getLeft() {
                    return left;
                }

                public void setLeft(int left) {
                    this.left = left;
                }
            }
        }

        public static class 地址Bean {
            /**
             * location : {"width":312,"top":136,"height":45,"left":216}
             * words : 宁波市州区山山镇明光北路1166号
             */

            private LocationBeanXXXXXXX location;
            private String words;

            public LocationBeanXXXXXXX getLocation() {
                return location;
            }

            public void setLocation(LocationBeanXXXXXXX location) {
                this.location = location;
            }

            public String getWords() {
                return words;
            }

            public void setWords(String words) {
                this.words = words;
            }

            public static class LocationBeanXXXXXXX {
                /**
                 * width : 312
                 * top : 136
                 * height : 45
                 * left : 216
                 */

                private int width;
                private int top;
                private int height;
                private int left;

                public int getWidth() {
                    return width;
                }

                public void setWidth(int width) {
                    this.width = width;
                }

                public int getTop() {
                    return top;
                }

                public void setTop(int top) {
                    this.top = top;
                }

                public int getHeight() {
                    return height;
                }

                public void setHeight(int height) {
                    this.height = height;
                }

                public int getLeft() {
                    return left;
                }

                public void setLeft(int left) {
                    this.left = left;
                }
            }
        }

        public static class 经营范围Bean {
            /**
             * location : {"width":388,"top":348,"height":250,"left":200}
             * words : 一市西量到形能雅而量批进记机英
             */

            private LocationBeanXXXXXXXX location;
            private String words;

            public LocationBeanXXXXXXXX getLocation() {
                return location;
            }

            public void setLocation(LocationBeanXXXXXXXX location) {
                this.location = location;
            }

            public String getWords() {
                return words;
            }

            public void setWords(String words) {
                this.words = words;
            }

            public static class LocationBeanXXXXXXXX {
                /**
                 * width : 388
                 * top : 348
                 * height : 250
                 * left : 200
                 */

                private int width;
                private int top;
                private int height;
                private int left;

                public int getWidth() {
                    return width;
                }

                public void setWidth(int width) {
                    this.width = width;
                }

                public int getTop() {
                    return top;
                }

                public void setTop(int top) {
                    this.top = top;
                }

                public int getHeight() {
                    return height;
                }

                public void setHeight(int height) {
                    this.height = height;
                }

                public int getLeft() {
                    return left;
                }

                public void setLeft(int left) {
                    this.left = left;
                }
            }
        }

        public static class 类型Bean {
            /**
             * location : {"width":195,"top":100,"height":28,"left":214}
             * words : 股份有限公司(l上
             */

            private LocationBeanXXXXXXXXX location;
            private String words;

            public LocationBeanXXXXXXXXX getLocation() {
                return location;
            }

            public void setLocation(LocationBeanXXXXXXXXX location) {
                this.location = location;
            }

            public String getWords() {
                return words;
            }

            public void setWords(String words) {
                this.words = words;
            }

            public static class LocationBeanXXXXXXXXX {
                /**
                 * width : 195
                 * top : 100
                 * height : 28
                 * left : 214
                 */

                private int width;
                private int top;
                private int height;
                private int left;

                public int getWidth() {
                    return width;
                }

                public void setWidth(int width) {
                    this.width = width;
                }

                public int getTop() {
                    return top;
                }

                public void setTop(int top) {
                    this.top = top;
                }

                public int getHeight() {
                    return height;
                }

                public void setHeight(int height) {
                    this.height = height;
                }

                public int getLeft() {
                    return left;
                }

                public void setLeft(int left) {
                    this.left = left;
                }
            }
        }

        public static class 有效期Bean {
            /**
             * location : {"width":249,"top":319,"height":27,"left":221}
             * words : 长期
             */

            private LocationBeanXXXXXXXXXX location;
            private String words;

            public LocationBeanXXXXXXXXXX getLocation() {
                return location;
            }

            public void setLocation(LocationBeanXXXXXXXXXX location) {
                this.location = location;
            }

            public String getWords() {
                return words;
            }

            public void setWords(String words) {
                this.words = words;
            }

            public static class LocationBeanXXXXXXXXXX {
                /**
                 * width : 249
                 * top : 319
                 * height : 27
                 * left : 221
                 */

                private int width;
                private int top;
                private int height;
                private int left;

                public int getWidth() {
                    return width;
                }

                public void setWidth(int width) {
                    this.width = width;
                }

                public int getTop() {
                    return top;
                }

                public void setTop(int top) {
                    this.top = top;
                }

                public int getHeight() {
                    return height;
                }

                public void setHeight(int height) {
                    this.height = height;
                }

                public int getLeft() {
                    return left;
                }

                public void setLeft(int left) {
                    this.left = left;
                }
            }
        }
    }
}
