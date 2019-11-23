import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { TaskUniteTestModule } from '../../../test.module';
import { UserInformationDeleteDialogComponent } from 'app/entities/user-information/user-information-delete-dialog.component';
import { UserInformationService } from 'app/entities/user-information/user-information.service';

describe('Component Tests', () => {
  describe('UserInformation Management Delete Component', () => {
    let comp: UserInformationDeleteDialogComponent;
    let fixture: ComponentFixture<UserInformationDeleteDialogComponent>;
    let service: UserInformationService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TaskUniteTestModule],
        declarations: [UserInformationDeleteDialogComponent]
      })
        .overrideTemplate(UserInformationDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserInformationDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserInformationService);
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
