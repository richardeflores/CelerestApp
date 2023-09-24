package com.huanghuang.kangshangyiliao.util;

public class Age {
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0043 A[ADDED_TO_REGION] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String age(java.lang.String r8) {
        /*
            java.text.SimpleDateFormat r0 = new java.text.SimpleDateFormat
            java.util.Locale r1 = java.util.Locale.getDefault()
            java.lang.String r2 = "yyyy-MM-dd"
            r0.<init>(r2, r1)
            r1 = 1
            r2 = 5
            r3 = 2
            r4 = 0
            java.util.Date r8 = r0.parse(r8)     // Catch:{ ParseException -> 0x002b }
            java.util.Calendar r0 = java.util.Calendar.getInstance()     // Catch:{ ParseException -> 0x002b }
            r0.setTime(r8)     // Catch:{ ParseException -> 0x002b }
            int r8 = r0.get(r1)     // Catch:{ ParseException -> 0x002b }
            int r5 = r0.get(r3)     // Catch:{ ParseException -> 0x0029 }
            int r4 = r0.get(r2)     // Catch:{ ParseException -> 0x0027 }
            goto L_0x0031
        L_0x0027:
            r0 = move-exception
            goto L_0x002e
        L_0x0029:
            r0 = move-exception
            goto L_0x002d
        L_0x002b:
            r0 = move-exception
            r8 = 0
        L_0x002d:
            r5 = 0
        L_0x002e:
            r0.printStackTrace()
        L_0x0031:
            java.util.Calendar r0 = java.util.Calendar.getInstance()
            int r1 = r0.get(r1)
            int r6 = r0.get(r3)
            int r7 = r0.get(r2)
            if (r8 > r1) goto L_0x00ea
            if (r8 != r1) goto L_0x0047
            if (r5 > r6) goto L_0x00ea
        L_0x0047:
            if (r8 != r1) goto L_0x004f
            if (r5 != r6) goto L_0x004f
            if (r4 <= r7) goto L_0x004f
            goto L_0x00ea
        L_0x004f:
            int r1 = r1 - r8
            int r6 = r6 - r5
            int r7 = r7 - r4
            if (r7 >= 0) goto L_0x005f
            int r6 = r6 + -1
            r8 = -1
            r0.add(r3, r8)
            int r8 = r0.getActualMaximum(r2)
            int r7 = r7 + r8
        L_0x005f:
            if (r6 >= 0) goto L_0x0067
            int r6 = r6 + 12
            int r6 = r6 % 12
            int r1 = r1 + -1
        L_0x0067:
            java.lang.String r8 = "岁"
            java.lang.String r0 = "0"
            r2 = 10
            if (r1 >= r2) goto L_0x0082
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r0)
            r3.append(r1)
            r3.append(r8)
            java.lang.String r8 = r3.toString()
            goto L_0x0091
        L_0x0082:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r1)
            r3.append(r8)
            java.lang.String r8 = r3.toString()
        L_0x0091:
            java.lang.String r3 = "月"
            if (r6 >= r2) goto L_0x00a8
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            r4.append(r0)
            r4.append(r6)
            r4.append(r3)
            java.lang.String r3 = r4.toString()
            goto L_0x00b7
        L_0x00a8:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            r4.append(r6)
            r4.append(r3)
            java.lang.String r3 = r4.toString()
        L_0x00b7:
            java.lang.String r4 = "天"
            if (r7 >= r2) goto L_0x00ce
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r2.append(r0)
            r2.append(r7)
            r2.append(r4)
            java.lang.String r0 = r2.toString()
            goto L_0x00dd
        L_0x00ce:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r0.append(r7)
            r0.append(r4)
            java.lang.String r0 = r0.toString()
        L_0x00dd:
            if (r1 != 0) goto L_0x00ec
            if (r6 != 0) goto L_0x00e8
            if (r7 != 0) goto L_0x00e6
            java.lang.String r8 = "1天"
            goto L_0x00ec
        L_0x00e6:
            r8 = r0
            goto L_0x00ec
        L_0x00e8:
            r8 = r3
            goto L_0x00ec
        L_0x00ea:
            java.lang.String r8 = ""
        L_0x00ec:
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huanghuang.kangshangyiliao.util.Age.age(java.lang.String):java.lang.String");
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x0045 A[ADDED_TO_REGION] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String age_phase(java.lang.String r10) {
        /*
            java.text.SimpleDateFormat r0 = new java.text.SimpleDateFormat
            java.util.Locale r1 = java.util.Locale.getDefault()
            java.lang.String r2 = "yyyy-MM-dd"
            r0.<init>(r2, r1)
            r1 = 5
            r2 = 2
            r3 = 1
            r4 = 0
            java.util.Date r10 = r0.parse(r10)     // Catch:{ ParseException -> 0x002b }
            java.util.Calendar r0 = java.util.Calendar.getInstance()     // Catch:{ ParseException -> 0x002b }
            r0.setTime(r10)     // Catch:{ ParseException -> 0x002b }
            int r10 = r0.get(r3)     // Catch:{ ParseException -> 0x002b }
            int r5 = r0.get(r2)     // Catch:{ ParseException -> 0x0029 }
            int r4 = r0.get(r1)     // Catch:{ ParseException -> 0x0027 }
            goto L_0x0031
        L_0x0027:
            r0 = move-exception
            goto L_0x002e
        L_0x0029:
            r0 = move-exception
            goto L_0x002d
        L_0x002b:
            r0 = move-exception
            r10 = 0
        L_0x002d:
            r5 = 0
        L_0x002e:
            r0.printStackTrace()
        L_0x0031:
            java.util.Calendar r0 = java.util.Calendar.getInstance()
            int r6 = r0.get(r3)
            int r7 = r0.get(r2)
            int r8 = r0.get(r1)
            java.lang.String r9 = "1"
            if (r10 > r6) goto L_0x0080
            if (r10 != r6) goto L_0x0049
            if (r5 > r7) goto L_0x0080
        L_0x0049:
            if (r10 != r6) goto L_0x0050
            if (r5 != r7) goto L_0x0050
            if (r4 <= r8) goto L_0x0050
            goto L_0x0080
        L_0x0050:
            int r6 = r6 - r10
            int r7 = r7 - r5
            int r8 = r8 - r4
            if (r8 >= 0) goto L_0x0060
            int r7 = r7 + -1
            r10 = -1
            r0.add(r2, r10)
            int r10 = r0.getActualMaximum(r1)
            int r8 = r8 + r10
        L_0x0060:
            if (r7 >= 0) goto L_0x0068
            int r7 = r7 + 12
            int r7 = r7 % 12
            int r6 = r6 + -1
        L_0x0068:
            if (r6 != 0) goto L_0x0073
            if (r7 != 0) goto L_0x0082
            r10 = 28
            if (r8 > r10) goto L_0x0082
            java.lang.String r9 = "0"
            goto L_0x0082
        L_0x0073:
            if (r6 > r3) goto L_0x0076
            goto L_0x0082
        L_0x0076:
            r10 = 14
            if (r6 > r10) goto L_0x007d
            java.lang.String r9 = "2"
            goto L_0x0082
        L_0x007d:
            java.lang.String r9 = "3"
            goto L_0x0082
        L_0x0080:
            java.lang.String r9 = ""
        L_0x0082:
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huanghuang.kangshangyiliao.util.Age.age_phase(java.lang.String):java.lang.String");
    }
}
