package com.example.asignment_gd2.controller;


import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.asignment_gd2.model.KichThuoc;
import com.example.asignment_gd2.model.MauSac;
import com.example.asignment_gd2.model.SanPham;
import com.example.asignment_gd2.model.SanPhamChiTiet;
import com.example.asignment_gd2.repository.KichThuocRepository;
import com.example.asignment_gd2.repository.MauSacRepository;
import com.example.asignment_gd2.repository.SanPhamChiTietRepository;
import com.example.asignment_gd2.repository.SanPhamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class SanPhamChiTietControllerTest {

    private MockMvc mockMvc;

    @Mock
    private SanPhamChiTietRepository sanPhamChiTietRepository;

    @Mock
    private SanPhamRepository sanPhamRepository;

    @Mock
    private MauSacRepository mauSacRepository;

    @Mock
    private KichThuocRepository kichThuocRepository;

    @InjectMocks
    private SanPhamChiTietController sanPhamChiTietController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(sanPhamChiTietController).build();
    }

    // Test hiển thị danh sách sản phẩm chi tiết
    @Test
    public void testShowSanPhamChiTiet() throws Exception {
        List<SanPhamChiTiet> sanPhamChiTietList = Arrays.asList(new SanPhamChiTiet(), new SanPhamChiTiet());
        when(sanPhamChiTietRepository.findAll()).thenReturn(sanPhamChiTietList);

        mockMvc.perform(get("/sanphamchitiet/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("sanphamchitiet/show"))
                .andExpect(model().attribute("list_SanPhamChiTiet", sanPhamChiTietList));
    }

    // Test tìm kiếm sản phẩm chi tiết theo mã sản phẩm
    @Test
    public void testSearchSPCT() throws Exception {
        // Dữ liệu giả lập trả về từ repository
        String maSPCT = "SPCT001";
        SanPhamChiTiet spct1 = new SanPhamChiTiet();
        spct1.setId(1);
        spct1.setMaSPCT("SPCT001");
        SanPhamChiTiet spct2 = new SanPhamChiTiet();
        spct2.setId(2);
        spct2.setMaSPCT("SPCT001");

        // Mock repository để trả về dữ liệu tìm kiếm
        when(sanPhamChiTietRepository.getList_SanPhamChiTietByMaSPCT(maSPCT)).thenReturn(Arrays.asList(spct1, spct2));

        // Thực hiện test
        mockMvc.perform(get("/sanphamchitiet/search")
                .param("maSPCT", maSPCT)) // Truyền tham số tìm kiếm
                .andExpect(status().isOk()) // Kiểm tra mã trạng thái trả về
                .andExpect(view().name("sanphamchitiet/show")) // Kiểm tra view trả về
                .andExpect(model().attribute("list_SanPhamChiTiet", hasSize(2))) // Kiểm tra kích thước danh sách
                .andExpect(model().attribute("list_SanPhamChiTiet", hasItem(
                        allOf(
                                hasProperty("id", is(1)),
                                hasProperty("maSPCT", is("SPCT001"))
                        )
                ))) // Kiểm tra đối tượng SPCT đầu tiên
                .andExpect(model().attribute("list_SanPhamChiTiet", hasItem(
                        allOf(
                                hasProperty("id", is(2)),
                                hasProperty("maSPCT", is("SPCT001"))
                        )
                ))) // Kiểm tra đối tượng SPCT thứ hai
        ;
    }
    


    // Test hiển thị form thêm sản phẩm chi tiết
    @Test
    public void testShowFormAdd() throws Exception {
        // Mock dữ liệu trả về từ repository
        when(mauSacRepository.findAll()).thenReturn(Arrays.asList(new MauSac(), new MauSac()));
        when(kichThuocRepository.findAll()).thenReturn(Arrays.asList(new KichThuoc(), new KichThuoc()));
        when(sanPhamRepository.findAll()).thenReturn(Arrays.asList(new SanPham(), new SanPham()));

        mockMvc.perform(get("/sanphamchitiet/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("sanphamchitiet/viewAdd"))
                .andExpect(model().attribute("list_MauSac", Arrays.asList(new MauSac(), new MauSac())))
                .andExpect(model().attribute("list_KichThuoc", Arrays.asList(new KichThuoc(), new KichThuoc())))
                .andExpect(model().attribute("list_SanPham", Arrays.asList(new SanPham(), new SanPham())));
    }

    // Test thêm sản phẩm chi tiết mới
    @Test
    public void testAddSPCT() throws Exception {
        SanPhamChiTiet sanPhamChiTiet = new SanPhamChiTiet();
        // Giả sử đã có dữ liệu hợp lệ để thêm
        sanPhamChiTiet.setMaSPCT("SPCT001");
        sanPhamChiTiet.setSoLuong(10);
        sanPhamChiTiet.setDonGia(100.0);
        sanPhamChiTiet.setTrangThai(true);

        // Mock hành vi của repository
        when(sanPhamChiTietRepository.save(sanPhamChiTiet)).thenReturn(sanPhamChiTiet);

        mockMvc.perform(post("/sanphamchitiet/add")
                .flashAttr("sanPhamChiTiet", sanPhamChiTiet))  // Truyền dữ liệu vào model
                .andExpect(status().is3xxRedirection())  // Kiểm tra mã trạng thái chuyển hướng (3xx)
                .andExpect(redirectedUrl("/sanphamchitiet/show"));  // Kiểm tra URL chuyển hướng
    }


    // Test xem chi tiết sản phẩm chi tiết
    @Test
    public void testDetailSanPhamChiTiet() throws Exception {
        Integer id = 1;
        SanPhamChiTiet sanPhamChiTiet = new SanPhamChiTiet();
        when(sanPhamChiTietRepository.getReferenceById(id)).thenReturn(sanPhamChiTiet);

        mockMvc.perform(get("/sanphamchitiet/detail")
                .param("id", id.toString()))
                .andExpect(status().isOk())
                .andExpect(view().name("sanphamchitiet/detail"))
                .andExpect(model().attribute("sanPhamChiTiet", sanPhamChiTiet));
    }
}
