package com.reddit.post.service.post.impl;

import com.reddit.post.dto.post.PostDto;
import com.reddit.post.dto.post.request.EditPostRequest;
import com.reddit.post.dto.post.request.PostRequestDto;
import com.reddit.post.exception.BadRequestException;
import com.reddit.post.exception.NotFoundException;
import com.reddit.post.feign.controller.UserClient;
import com.reddit.post.mapper.post.PostMapper;
import com.reddit.post.mapper.user.UserMapper;
import com.reddit.post.model.category.Category;
import com.reddit.post.repository.PostRepository;
import com.reddit.post.security.JwtTokenUtil;
import com.reddit.post.service.category.impl.CategoryService;
import com.reddit.post.service.cloudinary.ICloudinary;
import com.reddit.post.service.post.IPost;
import com.reddit.post.service.user.IUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService implements IPost {

    private final PostRepository postRepository;

    private final PostMapper postMapper;

    private final UserClient userClient;

    private final ICloudinary cloudinary;

    private final CategoryService categoryService;

    private final UserMapper userMapper;

    private final IUser iUser;

    private final JwtTokenUtil jwtTokenUtil;


    @Override
    @Transactional
    public PostDto savePost(PostRequestDto requestDto, MultipartFile multipartFile, String jwt) {

        Set<Category> categories = new HashSet<>();

        //feign
        //var checkIfUserExists = userClient.getPostedByDtoByUserId(requestDto.getUserId());
        var checkIfUserExists = userClient.findUserByUsername(jwtTokenUtil.getUsernameByJwt(jwt));

        if (checkIfUserExists == null) {
            throw new  NotFoundException("The user was not found");
        }

        var user = iUser.saveUser(userMapper.postedByToEntity(checkIfUserExists));

        if ((requestDto.getImageUrl() == null || requestDto.getImageUrl().isBlank()) && multipartFile == null)
            throw new BadRequestException("Bad request on post save");

        if (multipartFile != null && (requestDto.getImageUrl() != null || !requestDto.getImageUrl().isBlank()))
            requestDto.setImageUrl(cloudinary.getUrlFromUploadedMedia(multipartFile));


        if (requestDto.getCategories() == null || requestDto.getCategories().isEmpty())
            throw new BadRequestException("Categories are empty or null");

        requestDto.getCategories().stream().forEach(item -> {
                categories.add(categoryService.getCategoryEntityById(item.getId()));
        });

        var toEntity = postMapper.fromPostRequestToEntity(requestDto);

        toEntity.setCreatedAt(LocalDateTime.now());
        toEntity.setCategories(categories);
        toEntity.setPostLikeDislike(new HashSet<>());

        toEntity.setUser(user);


        var toSave = postRepository.save(toEntity);

        var toDto = postMapper.fromEntityToPostDto(toSave);


        return toDto;
    }

    @Override
    public List<PostDto> getAllPosts() {
        var fetchAllPosts = postRepository.findAll();
        return fetchAllPosts.stream().map(map -> postMapper.fromEntityToPostDto(map)).collect(Collectors.toList());
    }

    @Override
    public PostDto getPostByPostId(Long id) {
        var fetchPostById = postRepository.findById(id);

        if (fetchPostById.isEmpty()) {
            throw new NotFoundException("Post does not exist");
        }

        var toDto = postMapper.fromEntityToPostDto(fetchPostById.get());

        return toDto;
    }

    @Override
    public Set<PostDto> getAllPostsByUserId(Long id) {
        var getAllPostsByUserId = postRepository.fetchAllPostsByUserId(id);

        return postMapper.collectionEntityToDtos(getAllPostsByUserId);
    }

    @Override
    public void deletePostById(Long id, String jwt) {
        var findPost = postRepository.findById(id).orElseThrow(() -> new NotFoundException("The post was not found"));
        var fetchCurrentUser = userClient.findUserByUsername(jwtTokenUtil.getUsernameByJwt(jwt));

        if (findPost.getUser().getId() != fetchCurrentUser.getId())
            throw new BadRequestException("This user cannot delete post");

/*        if (findPost.getImageUrl().indexOf("https://res.cloudinary.com/") != 0) {
            //cloudinary
            try {
                cloudinary.deleteMedia(Arrays.asList(findPost.getImageUrl()));
            }catch (Exception e) {
                throw new NotFoundException("Temp");
            }

        }*/

        postRepository.deleteById(findPost.getId());
        log.info("Delete post id " + findPost.getId());
    }

    @Override
    @Transactional
    public PostDto editPostById(Long id, EditPostRequest request, String jwt) {

        Set<Category> categoryDtos = new HashSet<>();
        var findPost = postRepository.findById(id).orElseThrow(() -> new NotFoundException("The post was not found"));
        var findUser = userClient.findUserByUsername(jwtTokenUtil.getUsernameByJwt(jwt));

        if (findUser == null) {
            throw new NotFoundException("The user was not found");
        }

        if (findUser.getId() != findPost.getUser().getId()) {
            throw new BadRequestException("Bad request, not the owner of the post");
        }

        findPost.setTitle(request.getTitle());
        findPost.setDescription(request.getDescription());
        findPost.setAllowComment(request.isAllowComment());
        findPost.setEditedAt(LocalDateTime.now());

        request.getCategoryDtos().forEach(item -> {
            categoryDtos.add(categoryService.getCategoryEntityById(item));
        });

        findPost.setCategories(categoryDtos);

        var save = postRepository.save(findPost);

        return postMapper.fromEntityToPostDto(save);
    }

    @Override
    public List<PostDto> getAllPostsByCategoryName(String categoryName) {
       var fetchPostByCategoryName = postRepository.getAllPostsByCategoryName(categoryName);

       if (fetchPostByCategoryName.isEmpty())
           throw new NotFoundException("Not found");

       return fetchPostByCategoryName.get().stream().map(item -> postMapper.fromEntityToPostDto(item)).collect(Collectors.toList());
    }
}
