package com.kitakokaken.CMS.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kitakokaken.CMS.dto.SiyoulogDto;
import com.kitakokaken.CMS.entity.Siyoulog;
import com.kitakokaken.CMS.repository.SiyoulogRepository;

@Service
public class SiyoulogService {

    @Autowired
    private SiyoulogRepository repository;


    // 使用log一覧取得
    public List<SiyoulogDto> findSiyoulogData(String order) {

        if (order.equals("desc")) {
            return repository.findSiyoulogDataDesc();
        }

        return repository.findSiyoulogDataAsc();
    }


    // 一般ユーザ：自分の使用log一覧
    public List<SiyoulogDto> findSiyoulogByUserId(Long userId) {
        return repository.findSiyoulogByUserId(userId);
    }


    // 使用logをIDで取得
    public Siyoulog findById(Long id) {

        return repository.findById(id)
                .orElseThrow(() ->
                    new RuntimeException("使用記録が見つかりません"));
    }

    // 使用log更新
    public void updateSiyoulog(Siyoulog siyoulog) {

    // DBにある元のデータを取得
    Siyoulog existing = repository.findById(siyoulog.getLog_id())
            .orElseThrow(() ->
                new RuntimeException("使用記録が見つかりません"));

    // 編集する項目だけ更新
    existing.setC_id(siyoulog.getC_id());
    existing.setM_id(siyoulog.getM_id());
    existing.setPTR_id(siyoulog.getPTR_id());
    existing.setBf_weight(siyoulog.getBf_weight());
    existing.setAf_weight(siyoulog.getAf_weight());

    // useday と user_id は元の値をそのまま維持する
    // existing.setUseday(...) はしない
    // existing.setUser_id(...) もしない

    repository.save(existing);
    }

    // 使用log新規登録
    public void createSiyoulog(Siyoulog siyoulog) {
        repository.save(siyoulog);
    }

    //使用log新規登録
    public void saveSiyoulog(Siyoulog siyoulog){
        repository.save(siyoulog);
    }

}