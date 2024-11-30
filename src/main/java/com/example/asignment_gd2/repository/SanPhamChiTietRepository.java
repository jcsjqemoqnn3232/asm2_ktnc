package com.example.asignment_gd2.repository;

import com.example.asignment_gd2.model.SanPhamChiTiet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface SanPhamChiTietRepository extends JpaRepository<SanPhamChiTiet, Integer> {

    @Query(nativeQuery = true,
    value = "SELECT * FROM SanPhamChiTiet WHERE maSPCT LIKE %:maSPCT%"
    )

    ArrayList<SanPhamChiTiet> getList_SanPhamChiTietByMaSPCT(String maSPCT);

//    Object getListSanPhamChiTietTimKiem(Integer idSearch);
    // Thêm phương thức phân trang
    Page<SanPhamChiTiet> findAll(Pageable pageable);

}
