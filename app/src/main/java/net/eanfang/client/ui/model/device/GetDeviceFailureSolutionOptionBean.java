package net.eanfang.client.ui.model.device;

import java.util.List;

/**
 * Created by jornl on 2017/9/29.
 */

public class GetDeviceFailureSolutionOptionBean {


    private List<BeanBean> bean;

    public List<BeanBean> getBean() {
        return bean;
    }

    public void setBean(List<BeanBean> bean) {
        this.bean = bean;
    }

    public static class BeanBean {


        private int id;
        /**
         * 解决方案标题
         */
        private String title;
        /**
         * 原因
         */
        private String causeInfo;
        /**
         * 检查过程
         */
        private String checkInfo;
        /**
         * 处理方法
         */
        private String disposeInfo;
        /**
         * 指导建议
         */
        private String adviceInfo;


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        /**
         * 解决方案标题
         */
        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        /**
         * 原因
         */
        public String getCauseInfo() {
            return causeInfo;
        }

        public void setCauseInfo(String causeInfo) {
            this.causeInfo = causeInfo;
        }

        /**
         * 检查过程
         */
        public String getCheckInfo() {
            return checkInfo;
        }

        public void setCheckInfo(String checkInfo) {
            this.checkInfo = checkInfo;
        }

        /**
         * 处理方法
         */
        public String getDisposeInfo() {
            return disposeInfo;
        }

        public void setDisposeInfo(String disposeInfo) {
            this.disposeInfo = disposeInfo;
        }

        /**
         * 指导建议
         */
        public String getAdviceInfo() {
            return adviceInfo;
        }

        public void setAdviceInfo(String adviceInfo) {
            this.adviceInfo = adviceInfo;
        }
    }
}