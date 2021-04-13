//package org.xiaogang.util;
//
//import com.google.common.collect.Lists;
//import io.github.benas.randombeans.EnhancedRandomBuilder;
//import io.github.benas.randombeans.api.EnhancedRandom;
//import io.github.benas.randombeans.api.Randomizer;
//import org.apache.commons.lang3.RandomStringUtils;
//import org.apache.commons.lang3.RandomUtils;
//
//import java.math.BigDecimal;
//import java.nio.charset.Charset;
//import java.time.LocalDate;
//import java.time.LocalTime;
//import java.util.Collections;
//import java.util.List;
//import java.util.Random;
//import java.util.Set;
//import java.util.function.Supplier;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
//public class TestHelper {
//
//    private static EnhancedRandom enhancedRandom = EnhancedRandomBuilder.aNewEnhancedRandomBuilder()
//            .stringLengthRange(5, 20)
//            .randomizationDepth(3)
//            .randomize(Long.TYPE, (Randomizer<Long>) () -> TestHelper.anyLong())
//            .randomize(BigDecimal.class, (Randomizer<BigDecimal>) () -> TestHelper.anyBigDecimal())
//            .randomize(Integer.TYPE, (Randomizer<Integer>) () -> TestHelper.anyInt())
//            .charset(Charset.forName("UTF-8")).scanClasspathForConcreteTypes(true)
//            .build();
//
//    public static LocalDate day(int day) {
//        return LocalDate.of(2016, 1, day + 1);
//    }
//
//    public static <T> List<T> generateMany(Supplier<T> s) {
//        return generateMany(s, TestHelper.anyInt(10));
//    }
//
//    public static <T> List<T> generateMany(Supplier<T> s, int number) {
//        return Stream.generate(s).limit(number).collect(Collectors.toList());
//    }
//
//    public static <T> List<T> generateMany(Class<T> clazz, int number) {
//        return Stream.generate(() -> newInstance(clazz)).limit(number).collect(Collectors.toList());
//    }
//
//    public static <T> T newInstance(Class<T> type) {
//        return enhancedRandom.nextObject(type);
//    }
//
//
//    public static <T> T randomIn(T... values) {
//        return values[RandomUtils.nextInt(0, values.length)];
//    }
//
//    public static boolean anyBoolean() {
//        return RandomUtils.nextBoolean();
//    }
//
//    /**
//     * 1 to 100 rate of true value
//     *
//     * @param trueSeed
//     * @return
//     */
//    public static boolean anyBoolean(int trueSeed) {
//        return anyInt(100) < trueSeed;
//    }
//
//    public static LocalTime anyLocalTime() {
//        return LocalTime.of(anyInt(23), anyInt(59));
//    }
//
//    public static int anyInt() {
//        return Math.abs(RandomUtils.nextInt());
//    }
//
//    public static Long anyLong(int max) {
//        return new Long(anyInt(max));
//    }
//
//    public static Long anyLong() {
//        return Math.abs(RandomUtils.nextLong());
//    }
//
//    public static String anyString() {
//        return anyString(10);
//    }
//
//    public static String anyString(int length) {
//        return RandomStringUtils.randomAlphabetic(length);
//    }
//
//    public static double anyDouble() {
//        return RandomUtils.nextDouble();
//    }
//
//    public static int anyInt(int max) {
//        return RandomUtils.nextInt(0, max) + 1; // avoid random number 0
//    }
//
//    public static <T> Set<T> randomChooseSubCollection(Set<T> source) {
//        Set<T> subCollection = source.stream().filter((obj) -> RandomUtils.nextBoolean()).collect(Collectors.toSet());
//        if (subCollection.size() == 0) {
//            subCollection.add(source.iterator().next());
//        }
//
//        return subCollection;
//    }
//
//    public static Stream<String> generateFuzzyString(String partOfName) {
//        return Lists.newArrayList(TestHelper.anyString() + partOfName, partOfName + TestHelper.anyString(),
//                TestHelper.anyString() + partOfName + TestHelper.anyString()).stream();
//    }
//
//    public static <T> Set<T> randomChooseSubCollection(Set<T> source, int size) {
//        return randomChooseSubCollection(source, RandomUtils.nextInt(0, source.size() - size), size);
//    }
//
//    public static <T> Set<T> randomChooseSubCollection(Set<T> source, long from, long size) {
//        return source.stream().skip(from).limit(size).collect(Collectors.toSet());
//    }
//
//    public static BigDecimal anyBigDecimal() {
//        return BigDecimal.valueOf(Math.abs(RandomUtils.nextDouble() * anyInt(1000)));
//    }
//
//    private static Random random = new Random();
//
//    /**
//     * 返回随机ID.
//     */
//    public static long randomId() {
//        return Math.abs(random.nextLong());
//    }
//
//    public static String randomChars(int count) {
//        return RandomStringUtils.randomAlphanumeric(count);
//    }
//
//    /**
//     * 返回随机名称, prefix字符串+5位随机数字.
//     */
//    public static String randomName(String prefix) {
//        return prefix + random.nextInt(10000);
//    }
//
//    /**
//     * 从输入list中随机返回一个对象.
//     */
//    public static <T> T randomOne(List<T> list) {
//        return list.get(1);
//    }
//
//    /**
//     * 从输入list中随机返回n个对象.
//     */
//    public static <T> List<T> randomSome(List<T> list, int n) {
//        Collections.shuffle(list);
//        return list.subList(0, n);
//    }
//
//    /**
//     * 从输入list中随机返回随机个对象.
//     */
//    public static <T> List<T> randomSome(List<T> list) {
//        int size = random.nextInt(list.size());
//        if (size == 0) {
//            size = 1;
//        }
//        return randomSome(list, size);
//    }
//
//}
