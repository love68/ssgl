package com.ssgl.bean;

import java.util.ArrayList;
import java.util.List;

public class BedExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BedExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(String value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(String value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(String value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(String value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(String value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(String value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLike(String value) {
            addCriterion("id like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotLike(String value) {
            addCriterion("id not like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<String> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<String> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(String value1, String value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(String value1, String value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andBedNoIsNull() {
            addCriterion("bed_no is null");
            return (Criteria) this;
        }

        public Criteria andBedNoIsNotNull() {
            addCriterion("bed_no is not null");
            return (Criteria) this;
        }

        public Criteria andBedNoEqualTo(Boolean value) {
            addCriterion("bed_no =", value, "bedNo");
            return (Criteria) this;
        }

        public Criteria andBedNoNotEqualTo(Boolean value) {
            addCriterion("bed_no <>", value, "bedNo");
            return (Criteria) this;
        }

        public Criteria andBedNoGreaterThan(Boolean value) {
            addCriterion("bed_no >", value, "bedNo");
            return (Criteria) this;
        }

        public Criteria andBedNoGreaterThanOrEqualTo(Boolean value) {
            addCriterion("bed_no >=", value, "bedNo");
            return (Criteria) this;
        }

        public Criteria andBedNoLessThan(Boolean value) {
            addCriterion("bed_no <", value, "bedNo");
            return (Criteria) this;
        }

        public Criteria andBedNoLessThanOrEqualTo(Boolean value) {
            addCriterion("bed_no <=", value, "bedNo");
            return (Criteria) this;
        }

        public Criteria andBedNoIn(List<Boolean> values) {
            addCriterion("bed_no in", values, "bedNo");
            return (Criteria) this;
        }

        public Criteria andBedNoNotIn(List<Boolean> values) {
            addCriterion("bed_no not in", values, "bedNo");
            return (Criteria) this;
        }

        public Criteria andBedNoBetween(Boolean value1, Boolean value2) {
            addCriterion("bed_no between", value1, value2, "bedNo");
            return (Criteria) this;
        }

        public Criteria andBedNoNotBetween(Boolean value1, Boolean value2) {
            addCriterion("bed_no not between", value1, value2, "bedNo");
            return (Criteria) this;
        }

        public Criteria andIsOccupyIsNull() {
            addCriterion("is_occupy is null");
            return (Criteria) this;
        }

        public Criteria andIsOccupyIsNotNull() {
            addCriterion("is_occupy is not null");
            return (Criteria) this;
        }

        public Criteria andIsOccupyEqualTo(Boolean value) {
            addCriterion("is_occupy =", value, "isOccupy");
            return (Criteria) this;
        }

        public Criteria andIsOccupyNotEqualTo(Boolean value) {
            addCriterion("is_occupy <>", value, "isOccupy");
            return (Criteria) this;
        }

        public Criteria andIsOccupyGreaterThan(Boolean value) {
            addCriterion("is_occupy >", value, "isOccupy");
            return (Criteria) this;
        }

        public Criteria andIsOccupyGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_occupy >=", value, "isOccupy");
            return (Criteria) this;
        }

        public Criteria andIsOccupyLessThan(Boolean value) {
            addCriterion("is_occupy <", value, "isOccupy");
            return (Criteria) this;
        }

        public Criteria andIsOccupyLessThanOrEqualTo(Boolean value) {
            addCriterion("is_occupy <=", value, "isOccupy");
            return (Criteria) this;
        }

        public Criteria andIsOccupyIn(List<Boolean> values) {
            addCriterion("is_occupy in", values, "isOccupy");
            return (Criteria) this;
        }

        public Criteria andIsOccupyNotIn(List<Boolean> values) {
            addCriterion("is_occupy not in", values, "isOccupy");
            return (Criteria) this;
        }

        public Criteria andIsOccupyBetween(Boolean value1, Boolean value2) {
            addCriterion("is_occupy between", value1, value2, "isOccupy");
            return (Criteria) this;
        }

        public Criteria andIsOccupyNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_occupy not between", value1, value2, "isOccupy");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}