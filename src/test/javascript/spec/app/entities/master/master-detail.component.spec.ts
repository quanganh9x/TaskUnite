import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TaskUniteTestModule } from '../../../test.module';
import { MasterDetailComponent } from 'app/entities/master/master-detail.component';
import { Master } from 'app/shared/model/master.model';

describe('Component Tests', () => {
  describe('Master Management Detail Component', () => {
    let comp: MasterDetailComponent;
    let fixture: ComponentFixture<MasterDetailComponent>;
    const route = ({ data: of({ master: new Master(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TaskUniteTestModule],
        declarations: [MasterDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(MasterDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MasterDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.master).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
