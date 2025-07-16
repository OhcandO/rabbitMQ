package com.koji.rabbitmq1.vo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections4.map.ListOrderedMap;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.*;

public class MoMap extends ListOrderedMap {

    /**
     * serail
     */
    private static final long serialVersionUID = 1706475858162491211L;

    public MoMap() {
        super();
    }

    public MoMap(Object key, Object value) {
        super();
        put(key, value);
    }

    public MoMap(Map<Object, Object> map) {
        super();
        if (map != null) {
            for (Entry<Object, Object> entry : map.entrySet()) {
                put(entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * CamelCase 형식으로 지정
     */
    @Override
    public Object put(Object key, Object value) {
        if (key.toString().contains("_")) {
            return super.put(CamelCaseUtil.convertToCalmelCase(key.toString()), value);
        } else {
            return super.put(key, value);
        }
    }

    /**
     * camelcase 형식을 준수안할 경우
     */
    public Object putKey(Object key, Object value) {
        return super.put(key, value);
    }

    /**
     * object에 담겨있는 string 데이타를 추출한다.
     */
    public String getString(Object key) {
        Object obj = super.get(key);

        if (obj != null) {
            if (obj instanceof String) {
                return (String) obj;
            } else if (obj instanceof BigDecimal) {
                return ((BigDecimal) obj).stripTrailingZeros().toPlainString();
            } else if (obj instanceof Integer) {
                return String.valueOf(obj);
            } else if (obj instanceof Long) {
                return String.valueOf(obj);
            } else if (obj instanceof Float) {
                return String.valueOf(obj);
            } else if (obj instanceof Double) {
                if (!Double.isNaN((Double) obj) && !Double.isInfinite((Double) obj)) {
                    return BigDecimal.valueOf((Double) obj).stripTrailingZeros().toPlainString();
                } else {
                    return String.valueOf(obj);
                }
            } else {
                return String.valueOf(obj);
            }
        }

        return "";
    }

    /**
     * postgre Json Object MoMap으로 converting
     */
    public MoMap getPgJson(Object key) {
        Object obj = super.get(key);
        if (obj != null) {
            ObjectMapper om = new ObjectMapper();
            try {
                return om.readValue(obj.toString(), MoMap.class);
            } catch (JsonProcessingException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return null;
    }

    /**
     * object에 담겨있는 int 데이타를 추출한다.
     */
    public int getInt(Object key) {
        Object obj = super.get(key);

        try {
            if (obj != null) {
                if (obj instanceof BigDecimal) {
                    return ((Number) obj).intValue();
                } else if (obj instanceof Integer) {
                    return (Integer) obj;
                } else if (obj instanceof Long) {
                    return ((Long) obj).intValue();
                } else if (obj instanceof Float) {
                    return ((Float) obj).intValue();
                } else if (obj instanceof Double) {
                    return ((Double) obj).intValue();
                } else if (obj instanceof String) {
                    return Integer.parseInt((String) obj);
                }
            }
        } catch (NumberFormatException e) {
            return 0;
        }

        return 0;
    }

    /**
     * object에 담겨있는 long 데이타를 추출한다.
     */
    public long getLong(Object key) {
        Object obj = super.get(key);

        try {
            if (obj != null) {
                if (obj instanceof BigDecimal) {
                    return ((Number) obj).longValue();
                } else if (obj instanceof Integer) {
                    return (Integer) obj;
                } else if (obj instanceof Long) {
                    return (Long) obj;
                } else if (obj instanceof Float) {
                    return ((Float) obj).longValue();
                } else if (obj instanceof Double) {
                    return ((Double) obj).longValue();
                } else if (obj instanceof String) {
                    return Long.parseLong((String) obj);
                }
            }
        } catch (NumberFormatException e) {
            return 0;
        }

        return 0;
    }

    /**
     * 더블데이터 추출
     */
    public double getDouble(Object key) {
        Object obj = super.get(key);

        try {
            if (obj != null) {
                if (obj instanceof BigDecimal) {
                    return ((Number) obj).doubleValue();
                } else if (obj instanceof Double) {
                    return (Double) obj;
                } else if (obj instanceof Long) {
                    return ((Long) obj).doubleValue();
                } else if (obj instanceof Float) {
                    return ((Float) obj).doubleValue();
                } else if (obj instanceof String) {
                    return Double.parseDouble((String) obj);
                } else if (obj instanceof Integer) {
                    return ((Integer) obj).doubleValue();
                }
            }
        } catch (NumberFormatException e) {
            return 0;
        }

        return 0;
    }

}
