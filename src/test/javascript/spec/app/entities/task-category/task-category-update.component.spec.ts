import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { TaskUniteTestModule } from '../../../test.module';
import { TaskCategoryUpdateComponent } from 'app/entities/task-category/task-category-update.component';
import { TaskCategoryService } from 'app/entities/task-category/task-category.service';
import { TaskCategory } from 'app/shared/model/task-category.model';

describe('Component Tests', () => {
  describe('TaskCategory Management Update Component', () => {
    let comp: TaskCategoryUpdateComponent;
    let fixture: ComponentFixture<TaskCategoryUpdateComponent>;
    let service: TaskCategoryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TaskUniteTestModule],
        declarations: [TaskCategoryUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TaskCategoryUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TaskCategoryUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TaskCategoryService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TaskCategory(123);
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
        const entity = new TaskCategory();
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
