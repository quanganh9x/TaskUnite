import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { TaskUniteTestModule } from '../../../test.module';
import { StatisticUpdateComponent } from 'app/entities/statistic/statistic-update.component';
import { StatisticService } from 'app/entities/statistic/statistic.service';
import { Statistic } from 'app/shared/model/statistic.model';

describe('Component Tests', () => {
  describe('Statistic Management Update Component', () => {
    let comp: StatisticUpdateComponent;
    let fixture: ComponentFixture<StatisticUpdateComponent>;
    let service: StatisticService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TaskUniteTestModule],
        declarations: [StatisticUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(StatisticUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(StatisticUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(StatisticService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Statistic(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Statistic();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
