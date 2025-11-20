package kr.ac.kumoh.s20220958.w11_mongodb_backend.controller

import kr.ac.kumoh.s20220958.w11_mongodb_backend.model.Song
import kr.ac.kumoh.s20220958.w11_mongodb_backend.service.SongService
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/songs")
@CrossOrigin(origins = ["http://localhost:5173"]) //프론트엔드 주소 추가
class SongController(
    private val service: SongService
) {
    @GetMapping
    fun getAllSongs(): List<Song> = service.getAllSongs()

    @GetMapping("/{id}")
    fun getSongById(@PathVariable id: String): Song? = service.getSongById(id)
}