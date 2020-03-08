/**
 * @author : dormi330
 * @date : 2020-03-03
 * description : 文件描述
 */

package org.dormi.learn.pojo.base;


import org.dormi.learn.utils.crypt.JsonSerializer;

public class BasePojo {
    @Override
    public String toString() {
        return JsonSerializer.toJson(this);
    }

    public String prettyJson() {
        return JsonSerializer.prettyJson(this);
    }
}
