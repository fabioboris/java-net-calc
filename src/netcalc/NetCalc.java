package netcalc;

import java.util.ArrayList;
import java.util.List;


public class NetCalc {
    private int bits = 0;

    private int netBits = 0;
    private int hostBits = 0;
    private long nets = 0;
    private long hosts = 0;

    private int subNetBits = 0;
    private int subHostBits = 0;
    private long subNets = 0;
    private long subHosts = 0;

    private String netMask;
    private String subNetMask;

    private String netAddress;

    private double log2(long l) {
        return Math.log(l) / Math.log(2);
    }

    private double pow2(double d) {
        return Math.pow(2, d);
    }

    private String lPad(String value, String pad, int size) {
        while (value.length() < size)
            value = pad + value;

        return value;
    }

    private String join(String[] arr, String str) {
        StringBuffer sb = new StringBuffer();
        int n = arr.length - 1;

        for (int i = 0; i < n; i++) {
            sb.append(arr[i]);
            sb.append(str);
        }
        sb.append(arr[n]);

        return sb.toString();
    }

    private String ipDecimalToBinary(String ip) {
        String[] octets = ip.split("\\.");
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < octets.length; i++)
            sb.append(lPad(Integer.toBinaryString(Integer.parseInt(octets[i])), "0", 8));

        return sb.toString();
    }

    private String ipBinaryToDecimal(String ip) {
        String[] octets = new String[4];

        for (int i = 0; i < octets.length; i++)
            octets[i] = String.valueOf(Integer.parseInt(ip.substring(i * 8, i * 8 + 8), 2));

        return this.join(octets, ".");
    }

    private long ipDecimalToLong(String ip) {
        return Long.parseLong(this.ipDecimalToBinary(ip), 2);
    }

    private String ipLongToDecimal(long ip) {
        return this.ipBinaryToDecimal(lPad(Long.toBinaryString(ip), "0", 32));
    }

    private String ipAdd(String ip, long add) {
        return this.ipLongToDecimal(this.ipDecimalToLong(ip) + add);
    }

    private boolean isNetAddress(String ip, int netBits) {
        return (Long.parseLong(this.ipDecimalToBinary(ip), 2) % pow2(this.bits - netBits) == 0);
    }

    private String getNetMask(int netBits) {
        return this.ipLongToDecimal((long)(pow2(this.bits) - pow2(this.bits - netBits)));
    }

    private String getBroadcast(String netAddress, int netBits) {
        return this.ipLongToDecimal(this.ipDecimalToLong(netAddress) + (long)this.pow2(this.bits - netBits) - 1);
    }

    public List getSubNetsList() {
        List l = new ArrayList();
        String[] s;
        for (int i = 0; i < this.subNets; i++) {
            s = new String[2];
            s[0] = this.ipAdd(this.netAddress, i * this.subHosts);
            s[1] = this.getBroadcast(s[0], this.netBits + this.subNetBits);
            l.add(s);
        }
        return l;
    }

    private void setNets() {
        this.nets = (long)pow2(this.netBits);
    }
    private void setHosts() {
        this.hosts = (long)pow2(this.hostBits);
    }
    private void setNetBits() {
        this.netBits = this.bits - this.hostBits;
    }
    private void setHostBits() {
        this.hostBits = this.bits - this.netBits;
    }
    private void setNetMask() {
        this.netMask = this.getNetMask(this.netBits);
    }

    public void setNetBits(int i) {
        this.netBits = i;
        this.setNets();
        this.setHostBits();
        this.setHosts();
        this.setNetMask();
    }
    public void setHostBits(int i) {
        this.hostBits = i;
        this.setHosts();
        this.setNetBits();
        this.setNets();
        this.setNetMask();
    }
    public void setNets(long l) {
        this.nets = l;
        this.netBits = (int)log2(this.nets);
        this.setHostBits();
        this.setHosts();
        this.setNetMask();
    }
    public void setHosts(long l) {
        this.hosts = l;
        this.hostBits = (int)log2(this.hosts);
        this.setNetBits();
        this.setNets();
        this.setNetMask();
    }

    public void setNetAddress(String s) {
        if (this.isNetAddress(s, this.netBits)) {
            this.netAddress = s;
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    private void setSubNets() {
        this.subNets = (long)pow2(this.subNetBits);
    }
    private void setSubHosts() {
        this.subHosts = (long)pow2(this.subHostBits);
    }
    private void setSubNetBits() {
        this.subNetBits = this.hostBits - this.subHostBits;
    }
    private void setSubHostBits() {
        this.subHostBits = this.hostBits - this.subNetBits;
    }
    private void setSubNetMask() {
        this.subNetMask = this.getNetMask(this.netBits + this.subNetBits);
    }

    public void setSubNetBits(int i) {
        this.subNetBits = i;
        this.setSubNets();
        this.setSubHostBits();
        this.setSubHosts();
        this.setSubNetMask();
    }
    public void setSubHostBits(int i) {
        this.subHostBits = i;
        this.setSubHosts();
        this.setSubNetBits();
        this.setSubNets();
        this.setSubNetMask();
    }
    public void setSubNets(long l) {
        this.subNets = l;
        this.subNetBits = (int)log2(this.subNets);
        this.setSubHostBits();
        this.setSubHosts();
        this.setSubNetMask();
    }
    public void setSubHosts(long l) {
        this.subHosts = l;
        this.subHostBits = (int)log2(this.subHosts);
        this.setSubNetBits();
        this.setSubNets();
        this.setSubNetMask();
    }

    public int getBits() {
        return this.bits;
    }

    public int getNetBits() {
        return this.netBits;
    }
    public int getHostBits() {
        return this.hostBits;
    }
    public long getNets() {
        return this.nets;
    }
    public long getHosts() {
        return this.hosts;
    }
    public String getNetMask() {
        return this.netMask;
    }

    public String getNetAddress() {
        return this.netAddress;
    }
    public String getNetBroadcast() {
        return this.getBroadcast(this.netAddress, this.netBits);
    }
    
    public int getSubNetBits() {
        return this.subNetBits;
    }
    public int getSubHostBits() {
        return this.subHostBits;
    }
    public long getSubNets() {
        return this.subNets;
    }
    public long getSubHosts() {
        return this.subHosts;
    }
    public String getSubNetMask() {
        return this.subNetMask;
    }

    NetCalc() {
        this.bits = 32;
        this.setNetBits(24);
        this.setSubNetBits(0);
        this.setNetAddress("192.168.0.0");
    }
}
