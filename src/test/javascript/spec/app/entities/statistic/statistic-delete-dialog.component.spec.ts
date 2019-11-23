import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { TaskUniteTestModule } from '../../../test.module';
import { StatisticDeleteDialogComponent } from 'app/entities/statistic/statistic-delete-dialog.component';
import { StatisticService } from 'app/entities/statistic/statistic.service';

describe('Component Tests', () => {
  describe('Statistic Management Delete Component', () => {
    let comp: StatisticDeleteDialogComponent;
    let fixture: ComponentFixture<StatisticDeleteDialogComponent>;
    let service: StatisticService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TaskUniteTestModule],
        declarations: [StatisticDeleteDialogComponent]
      })
        .overrideTemplate(StatisticDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(StatisticDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(StatisticService);
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
