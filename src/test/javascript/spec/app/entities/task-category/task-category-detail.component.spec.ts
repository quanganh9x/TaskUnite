import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TaskUniteTestModule } from '../../../test.module';
import { TaskCategoryDetailComponent } from 'app/entities/task-category/task-category-detail.component';
import { TaskCategory } from 'app/shared/model/task-category.model';

describe('Component Tests', () => {
  describe('TaskCategory Management Detail Component', () => {
    let comp: TaskCategoryDetailComponent;
    let fixture: ComponentFixture<TaskCategoryDetailComponent>;
    const route = ({ data: of({ taskCategory: new TaskCategory(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TaskUniteTestModule],
        declarations: [TaskCategoryDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TaskCategoryDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TaskCategoryDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.taskCategory).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
