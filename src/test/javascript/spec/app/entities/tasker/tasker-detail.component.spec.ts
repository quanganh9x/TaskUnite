import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TaskUniteTestModule } from '../../../test.module';
import { TaskerDetailComponent } from 'app/entities/tasker/tasker-detail.component';
import { Tasker } from 'app/shared/model/tasker.model';

describe('Component Tests', () => {
  describe('Tasker Management Detail Component', () => {
    let comp: TaskerDetailComponent;
    let fixture: ComponentFixture<TaskerDetailComponent>;
    const route = ({ data: of({ tasker: new Tasker(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TaskUniteTestModule],
        declarations: [TaskerDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TaskerDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TaskerDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tasker).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
