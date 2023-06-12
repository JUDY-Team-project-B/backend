package com.hangout.hangout.global.common.domain.entity;

public enum SearchType {
    title {
        @Override
        public String toString() {
            return "title";
        }
    },
    context {
        @Override
        public String toString() {
            return "context";
        }
    },
    tag {
        @Override
        public String toString() {
            return "tag";
        }
    },
    nickname {
        @Override
        public String toString() {
            return "nickname";
        }
    },
    all {
        @Override
        public String toString() {
            return "all";
        }
    }
}
