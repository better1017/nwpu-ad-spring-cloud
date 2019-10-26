package edu.nwpu.ad.index;

/**
 * 广告数据索引
 * 不是所有的表的都需要构造索引
 * 只有需要检索的表才需要构造索引
 * 不是所有字段都需要索引
 */
public interface IndexAware<K, V> {
    V get(K key);

    void add(K key, V value);

    void update(K key, V value);

    void delete(K key, V value);
}
