package libraly.candelsmadebynikol.services.implementation;

import libraly.candelsmadebynikol.models.dto.AddCategoryDTO;
import libraly.candelsmadebynikol.models.entity.CategoryEntity;
import libraly.candelsmadebynikol.repository.CategoryRepository;
import libraly.candelsmadebynikol.services.interfaces.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static libraly.candelsmadebynikol.common.exceptions.ExceptionMessages.CATEGORY_ALREADY_EXIST_EXCEPTION;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void addCategory(AddCategoryDTO addCategoryDTO) {

        if (categoryRepository.findByName(addCategoryDTO.getName()).isPresent()) {
            throw new IllegalArgumentException(CATEGORY_ALREADY_EXIST_EXCEPTION);
        }

        CategoryEntity newCategory = new CategoryEntity();
        newCategory.setName(addCategoryDTO.getName());
        newCategory.setDescription(addCategoryDTO.getDescription());
        categoryRepository.save(newCategory);
    }
}
