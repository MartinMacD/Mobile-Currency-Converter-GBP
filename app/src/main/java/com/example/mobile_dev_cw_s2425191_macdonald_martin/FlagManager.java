//
// Name                 Martin MacDonald
// Student ID           s2425191
// Programme of Study   BSc (Hons) Software Development
//

package com.example.mobile_dev_cw_s2425191_macdonald_martin;

import java.util.HashMap;

public class FlagManager {
    private HashMap<String, Integer> flagMap = new HashMap<>();

    public FlagManager(){
        //All flags with their codes
        flagMap.put("GBP",R.drawable.flag_gb);
        flagMap.put("USD",R.drawable.flag_us);
        flagMap.put("EUR",R.drawable.flag_eu);
        flagMap.put("JPY",R.drawable.flag_jp);
        flagMap.put("AED",R.drawable.flag_ae);
        flagMap.put("ANG",R.drawable.flag_nl);
        flagMap.put("ARS",R.drawable.flag_ar);
        flagMap.put("AUD",R.drawable.flag_au);
        flagMap.put("BDT",R.drawable.flag_bd);
        flagMap.put("BGN",R.drawable.flag_bg);
        flagMap.put("BHD",R.drawable.flag_bh);
        flagMap.put("BND",R.drawable.flag_bn);
        flagMap.put("BOB",R.drawable.flag_bo);
        flagMap.put("BRL",R.drawable.flag_br);
        flagMap.put("BWP",R.drawable.flag_bw);
        flagMap.put("CAD",R.drawable.flag_ca);
        flagMap.put("CHF",R.drawable.flag_ch);
        flagMap.put("CLP",R.drawable.flag_cl);
        flagMap.put("CNY",R.drawable.flag_cn);
        flagMap.put("COP",R.drawable.flag_co);
        flagMap.put("CRC",R.drawable.flag_cr);
        flagMap.put("CZK",R.drawable.flag_cz);
        flagMap.put("DKK",R.drawable.flag_dk);
        flagMap.put("DOP",R.drawable.flag_dm);
        flagMap.put("DZD",R.drawable.flag_dz);
        flagMap.put("EEK",R.drawable.flag_ee);
        flagMap.put("EGP",R.drawable.flag_eg);
        flagMap.put("FJD",R.drawable.flag_fj);
        flagMap.put("HKD",R.drawable.flag_hk);
        flagMap.put("HNL",R.drawable.flag_hn);
        flagMap.put("HRK",R.drawable.flag_hr);
        flagMap.put("HUF",R.drawable.flag_id);
        flagMap.put("ILS",R.drawable.flag_il);
        flagMap.put("INR",R.drawable.flag_in);
        flagMap.put("ISK",R.drawable.flag_is);
        flagMap.put("JOD",R.drawable.flag_jo);
        flagMap.put("KES",R.drawable.flag_ke);
        flagMap.put("KRW",R.drawable.flag_kr);
        flagMap.put("KWD",R.drawable.flag_kw);
        flagMap.put("KYD",R.drawable.flag_ky);
        flagMap.put("KZT",R.drawable.flag_kz);
        flagMap.put("LBP",R.drawable.flag_lb);
        flagMap.put("LKR",R.drawable.flag_lk);
        flagMap.put("LTL",R.drawable.flag_lt);
        flagMap.put("LVL",R.drawable.flag_lv);
        flagMap.put("MAD",R.drawable.flag_ma);
        flagMap.put("MDL",R.drawable.flag_md);
        flagMap.put("MKD",R.drawable.flag_mk);
        flagMap.put("MUR",R.drawable.flag_mu);
        flagMap.put("MVR",R.drawable.flag_mv);
        flagMap.put("MXN",R.drawable.flag_mx);
        flagMap.put("MYR",R.drawable.flag_my);
        flagMap.put("NAD",R.drawable.flag_na);
        flagMap.put("NGN",R.drawable.flag_ng);
        flagMap.put("NIO",R.drawable.flag_ni);
        flagMap.put("NOK",R.drawable.flag_no);
        flagMap.put("NPR",R.drawable.flag_np);
        flagMap.put("NZD",R.drawable.flag_nz);
        flagMap.put("OMR",R.drawable.flag_om);
        flagMap.put("PEN",R.drawable.flag_pe);
        flagMap.put("PGK",R.drawable.flag_pg);
        flagMap.put("PHP",R.drawable.flag_ph);
        flagMap.put("PKR",R.drawable.flag_pk);
        flagMap.put("PLN",R.drawable.flag_pl);
        flagMap.put("PYG",R.drawable.flag_py);
        flagMap.put("QAR",R.drawable.flag_qa);
        flagMap.put("RON",R.drawable.flag_ro);
        flagMap.put("RSD",R.drawable.flag_rs);
        flagMap.put("RUB",R.drawable.flag_ru);
        flagMap.put("SAR",R.drawable.flag_sa);
        flagMap.put("SCR",R.drawable.flag_sc);
        flagMap.put("SEK",R.drawable.flag_se);
        flagMap.put("SGD",R.drawable.flag_sg);
        flagMap.put("SKK",R.drawable.flag_sk);
        flagMap.put("SLL",R.drawable.flag_sl);
        flagMap.put("SVC",R.drawable.flag_sv);
        flagMap.put("THB",R.drawable.flag_th);
        flagMap.put("TND",R.drawable.flag_tn);
        flagMap.put("TRY",R.drawable.flag_tr);
        flagMap.put("TTD",R.drawable.flag_tt);
        flagMap.put("TWD",R.drawable.flag_tw);
        flagMap.put("TZS",R.drawable.flag_tz);
        flagMap.put("UAH",R.drawable.flag_ua);
        flagMap.put("UGX",R.drawable.flag_ug);
        flagMap.put("UYU",R.drawable.flag_uy);
        flagMap.put("UZS",R.drawable.flag_uz);
        flagMap.put("VEF",R.drawable.flag_ve);
        flagMap.put("VND",R.drawable.flag_vn);
        flagMap.put("XOF",R.drawable.flag_gh);
        flagMap.put("YER",R.drawable.flag_ye);
        flagMap.put("ZAR",R.drawable.flag_za);
        flagMap.put("ZMK",R.drawable.flag_zm);
        flagMap.put("IQD",R.drawable.flag_iq);
        flagMap.put("ALL",R.drawable.flag_al);
        flagMap.put("BSD",R.drawable.flag_bs);
        flagMap.put("BBD",R.drawable.flag_bb);
        flagMap.put("BZD",R.drawable.flag_bz);
        flagMap.put("BTN",R.drawable.flag_bt);
        flagMap.put("BIF",R.drawable.flag_bi);
        flagMap.put("KHR",R.drawable.flag_kh);
        flagMap.put("CVE",R.drawable.flag_cv);
        flagMap.put("BEAC",R.drawable.flag_cm);
        flagMap.put("KMF",R.drawable.flag_km);
        flagMap.put("CUP",R.drawable.flag_cu);
        flagMap.put("DJF",R.drawable.flag_dj);
        flagMap.put("XCD",R.drawable.flag_ag);
        flagMap.put("ETB",R.drawable.flag_et);
        flagMap.put("GMD",R.drawable.flag_gm);
        flagMap.put("GTQ",R.drawable.flag_gt);
        flagMap.put("GNF",R.drawable.flag_gn);
        flagMap.put("GYD",R.drawable.flag_gy);
        flagMap.put("HTG",R.drawable.flag_ht);
        flagMap.put("IRR",R.drawable.flag_ir);
        flagMap.put("JMD",R.drawable.flag_jm);
        flagMap.put("LAK",R.drawable.flag_la);
        flagMap.put("LSL",R.drawable.flag_ls);
        flagMap.put("LRD",R.drawable.flag_lr);
        flagMap.put("LYD",R.drawable.flag_ly);
        flagMap.put("MOP",R.drawable.flag_mo);
        flagMap.put("MWK",R.drawable.flag_mw);
        flagMap.put("MRO",R.drawable.flag_mr);
        flagMap.put("MNT",R.drawable.flag_mn);
        flagMap.put("MMK",R.drawable.flag_mm);
        flagMap.put("KPW",R.drawable.flag_kp);
        flagMap.put("RWF",R.drawable.flag_rw);
        flagMap.put("WST",R.drawable.flag_ws);
        flagMap.put("SBD",R.drawable.flag_sb);
        flagMap.put("SOS",R.drawable.flag_so);
        flagMap.put("SDG",R.drawable.flag_sd);
        flagMap.put("SZL",R.drawable.flag_sz);
        flagMap.put("SYP",R.drawable.flag_sy);
        flagMap.put("TOP",R.drawable.flag_to);
        flagMap.put("VUV",R.drawable.flag_vu);
        flagMap.put("KGS",R.drawable.flag_kg);
        flagMap.put("GHS",R.drawable.flag_gh);
        flagMap.put("BYN",R.drawable.flag_by);
        flagMap.put("AFN",R.drawable.flag_af);
        flagMap.put("AOA",R.drawable.flag_ao);
        flagMap.put("AMD",R.drawable.flag_am);
        flagMap.put("AZN",R.drawable.flag_az);
        flagMap.put("BAM",R.drawable.flag_ba);
        flagMap.put("CDF",R.drawable.flag_cd);
        flagMap.put("ERN",R.drawable.flag_er);
        flagMap.put("GEL",R.drawable.flag_ge);
        flagMap.put("MGA",R.drawable.flag_mg);
        flagMap.put("MZN",R.drawable.flag_mz);
        flagMap.put("SRD",R.drawable.flag_sr);
        flagMap.put("TJS",R.drawable.flag_tj);
        flagMap.put("TMT",R.drawable.flag_tm);
        flagMap.put("ZMW",R.drawable.flag_zm);
        flagMap.put("AWG",R.drawable.flag_aw);
        flagMap.put("BYR",R.drawable.flag_by);
        flagMap.put("BMD",R.drawable.flag_bm);
        flagMap.put("FKP",R.drawable.flag_fk);
        flagMap.put("XPF",R.drawable.flag_pf);
        flagMap.put("PAB",R.drawable.flag_pa);
        flagMap.put("STD",R.drawable.flag_st);
        flagMap.put("SHP",R.drawable.flag_sh);
        flagMap.put("ZWD",R.drawable.flag_zw);
    }

    public int getFlag(String code){
        Integer flag = flagMap.get(code.toUpperCase());
        // return a default flag, or 0 if no flag available
        if(flag == null){
            return R.drawable.flag_us;
        }
        return flag;
    }


}
