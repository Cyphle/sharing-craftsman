package fr.knowledge.command.api.library;

import fr.knowledge.command.api.common.AuthorizationInfoDTO;
import fr.knowledge.command.api.common.AuthorizationService;
import fr.knowledge.command.bus.CommandBus;
import fr.knowledge.domain.library.aggregates.UpdateCategoryException;
import fr.knowledge.domain.library.commands.AddKnowledgeCommand;
import fr.knowledge.domain.library.commands.CreateCategoryCommand;
import fr.knowledge.domain.library.commands.DeleteCategoryCommand;
import fr.knowledge.domain.library.commands.UpdateCategoryCommand;
import fr.knowledge.domain.library.exceptions.AddKnowledgeException;
import fr.knowledge.domain.library.exceptions.AlreadyExistingCategoryException;
import fr.knowledge.domain.library.exceptions.CategoryNotFoundException;
import fr.knowledge.domain.library.exceptions.CreateCategoryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class LibraryService {
  private CommandBus commandBus;
  private AuthorizationService authorizationService;

  @Autowired
  public LibraryService(AuthorizationService authorizationService, CommandBus commandBus) {
    this.authorizationService = authorizationService;
    this.commandBus = commandBus;
  }

  public ResponseEntity createCategory(AuthorizationInfoDTO authorizationInfoDTO, CategoryDTO categoryDTO) {
    if (!authorizationService.isUserAuthorized(authorizationInfoDTO))
      return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);

    CreateCategoryCommand command = new CreateCategoryCommand(categoryDTO.getName());
    try {
      commandBus.send(command);
    } catch (AlreadyExistingCategoryException e) {
      return ResponseEntity.badRequest().body("Category already exists.");
    } catch (CreateCategoryException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
    return ResponseEntity.ok().build();
  }

  public ResponseEntity updateCategory(AuthorizationInfoDTO authorizationInfoDTO, CategoryDTO categoryDTO) {
    if (!authorizationService.isUserAuthorized(authorizationInfoDTO))
      return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);

    UpdateCategoryCommand command = new UpdateCategoryCommand(categoryDTO.getId(), categoryDTO.getName());
    try {
      commandBus.send(command);
    } catch(CategoryNotFoundException e) {
      return new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND);
    } catch (UpdateCategoryException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
    return ResponseEntity.ok().build();
  }

  public ResponseEntity deleteCategory(AuthorizationInfoDTO authorizationInfoDTO, CategoryDTO categoryDTO) {
    if (!authorizationService.isUserAuthorized(authorizationInfoDTO))
      return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);

    DeleteCategoryCommand command = new DeleteCategoryCommand(categoryDTO.getId());
    try {
      commandBus.send(command);
    } catch (CategoryNotFoundException e) {
      return new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
    return ResponseEntity.ok().build();
  }

  public ResponseEntity addKnowledge(AuthorizationInfoDTO authorizationInfoDTO, KnowledgeDTO knowledgeDTO) {
    if (!authorizationService.isUserAuthorized(authorizationInfoDTO))
      return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);

    AddKnowledgeCommand command = new AddKnowledgeCommand(knowledgeDTO.getCategoryId(), knowledgeDTO.getCreator(), knowledgeDTO.getTitle(), knowledgeDTO.getContent());
    try {
      commandBus.send(command);
    } catch (CategoryNotFoundException e) {
      return new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND);
    } catch (AddKnowledgeException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
    return ResponseEntity.ok().build();
  }
}
