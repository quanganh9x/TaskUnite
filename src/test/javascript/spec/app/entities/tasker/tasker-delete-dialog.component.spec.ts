import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { TaskUniteTestModule } from '../../../test.module';
import { TaskerDeleteDialogComponent } from 'app/entities/tasker/tasker-delete-dialog.component';
import { TaskerService } from 'app/entities/tasker/tasker.service';

describe('Component Tests', () => {
  describe('Tasker Management Delete Component', () => {
    let comp: TaskerDeleteDialogComponent;
    let fixture: ComponentFixture<TaskerDeleteDialogComponent>;
    let service: TaskerService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TaskUniteTestModule],
        declarations: [TaskerDeleteDialogComponent]
      })
        .overrideTemplate(TaskerDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TaskerDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TaskerService);
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
