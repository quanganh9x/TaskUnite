import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { TaskUniteTestModule } from '../../../test.module';
import { TaskerUpdateComponent } from 'app/entities/tasker/tasker-update.component';
import { TaskerService } from 'app/entities/tasker/tasker.service';
import { Tasker } from 'app/shared/model/tasker.model';

describe('Component Tests', () => {
  describe('Tasker Management Update Component', () => {
    let comp: TaskerUpdateComponent;
    let fixture: ComponentFixture<TaskerUpdateComponent>;
    let service: TaskerService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TaskUniteTestModule],
        declarations: [TaskerUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TaskerUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TaskerUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TaskerService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Tasker(123);
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
        const entity = new Tasker();
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
