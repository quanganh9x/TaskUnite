import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { TaskUniteTestModule } from '../../../test.module';
import { TaskCategoryDeleteDialogComponent } from 'app/entities/task-category/task-category-delete-dialog.component';
import { TaskCategoryService } from 'app/entities/task-category/task-category.service';

describe('Component Tests', () => {
  describe('TaskCategory Management Delete Component', () => {
    let comp: TaskCategoryDeleteDialogComponent;
    let fixture: ComponentFixture<TaskCategoryDeleteDialogComponent>;
    let service: TaskCategoryService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TaskUniteTestModule],
        declarations: [TaskCategoryDeleteDialogComponent]
      })
        .overrideTemplate(TaskCategoryDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TaskCategoryDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TaskCategoryService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
