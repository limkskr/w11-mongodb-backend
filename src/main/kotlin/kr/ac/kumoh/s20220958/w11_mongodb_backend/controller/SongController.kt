package kr.ac.kumoh.s20220958.w11_mongodb_backend.controller

import kr.ac.kumoh.s20220958.w11_mongodb_backend.model.Song
import kr.ac.kumoh.s20220958.w11_mongodb_backend.service.SongService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import java.net.URI

@RestController
@RequestMapping("/api/songs")
@CrossOrigin(origins = ["http://localhost:5173"])

class SongController(
    private val service: SongService
) {
    // Create
    @PostMapping
    fun addSong(
        @RequestBody song: Song
    ): ResponseEntity<Song> {
        val createdSong = service.addSong(song)
        // 201 Created
        // 생성된 리소스의 URI 반환
        return ResponseEntity
            .created(URI("/api/songs/${createdSong.id}"))
            .body(createdSong)
    }

    // Read (Retrieve)
    @GetMapping
    fun getAllSongs(): ResponseEntity<List<Song>> {
        val songs = service.getAllSongs()
        if (songs.isEmpty()) {
            // 204 No Content
            return ResponseEntity.noContent().build()
        }

        // 200 OK
        return ResponseEntity.ok(songs)
    }

    @GetMapping("/{id}")
    fun getSongById(
        @PathVariable id: String
    ): ResponseEntity<Song> {
        val song = service.getSongById(id)

        // 있으면 200 OK, 없으면 404 Not Found
        return song?.let {
            ResponseEntity.ok(it)
        } ?: ResponseEntity.notFound().build()
    }

    @GetMapping("/singer/{singer}")
    fun getSongBySinger(
        @PathVariable singer: String
    ): ResponseEntity<List<Song>> {
        val songs = service.getSongBySinger(singer)

        if (songs.isEmpty()) {
            // 204 No Content
            return ResponseEntity.noContent().build()
        }

        // 200 OK
        return ResponseEntity.ok(songs)
    }

    // Update
    @PutMapping("/{id}")
    fun updateSong(
        @PathVariable id: String,
        @RequestBody songDetails: Song
    ): ResponseEntity<Song> {
        val updatedSong = service.updateSong(id, songDetails)

        // 성공시 200 OK와 업데이트된 객체 반환, 실패시 404 Not Found
        return updatedSong?.let {
            ResponseEntity.ok(it)
        } ?: ResponseEntity.notFound().build()
    }

    // Delete
    @DeleteMapping("/{id}")
    fun deleteSong(
        @PathVariable id: String
    ): ResponseEntity<Void> {
        return if (service.deleteSong(id)) {
            // 204 No Content
            ResponseEntity.noContent().build()
        } else {
            // 404 Not Found
            ResponseEntity.notFound().build()
        }
    }
}


// responseEntity 안쓸 때
/*

@RestController
@RequestMapping("/api/songs")
@CrossOrigin(origins = ["http://localhost:5173"]) //프론트엔드 주소 추가
class SongController(
    private val service: SongService
) {
    // CREAT
    @PostMapping
    fun addSong(@RequestBody song: Song): Song = service.addSong(song)


    // READ or RETRIEVE
    @GetMapping
    fun getAllSongs(): List<Song> = service.getAllSongs()

    @GetMapping("/{id}")
    fun getSongById(@PathVariable id: String): Song? = service.getSongById(id)

    @GetMapping("/singer/{singer}")
    fun getSongBySinger(@PathVariable singer: String): List<Song> = service.getSongBySinger(singer)

    // Update
    @PutMapping("/{id}")
    fun updateSong(
        @PathVariable id: String,
        @RequestBody songDetails: Song
    ): Song? =  service.updateSong(id, songDetails)

    // Delete
    @DeleteMapping("/{id}")
    fun deleteSong(@PathVariable id: String): Map<String, String> {
        return if (service.deleteSong(id))
            mapOf("status" to "deleted")
        else
            mapOf("status" to "not found")
    }
}*/
