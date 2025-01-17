package wateradmin.controller.mot;

import org.noear.solon.Utils;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.util.DateAnalyzer;
import org.noear.water.WW;
import org.noear.water.model.TagCountsM;
import org.noear.water.protocol.model.log.LogModel;
import org.noear.water.utils.Datetime;
import org.noear.water.utils.TextUtils;


import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.core.handle.ModelAndView;
import wateradmin.controller.BaseController;
import wateradmin.dso.SettingUtils;
import wateradmin.dso.TagChecker;
import wateradmin.dso.TagUtil;
import wateradmin.dso.db.DbWaterLogApi;
import wateradmin.models.ScaleType;

import java.util.Comparator;
import java.util.List;


@Controller
@Mapping("/mot/")
public class SqlController extends BaseController {

    private final static String logger = WW.logger_water_log_sql_p;

    //消息异常记录
    @Mapping("sql")
    public ModelAndView sql(Context ctx, String tag_name) throws Exception {
        if (SettingUtils.serviceScale().ordinal() < ScaleType.medium.ordinal()) {
            ctx.forward("/mot/sql/inner");
            return null;
        }

        List<TagCountsM> tags = DbWaterLogApi.getSqlGroupsByLogger(logger);

        TagChecker.filter(tags, m -> m.tag);

        tag_name = TagUtil.build(tag_name, tags);

        viewModel.put("tags", tags);
        viewModel.put("tag_name", tag_name);

        return view("mot/sql");
    }


    /**
     * state: ALL,SELECT,UPDATE,INSERT,DELETE,OTHER
     */
    @Mapping("sql/inner")
    public ModelAndView sql_inner(String tag_name, String serviceName, int seconds, String time, Integer _state, long startId) throws Exception {
        if (SettingUtils.serviceScale().ordinal() < ScaleType.medium.ordinal()) {
            tag_name = null;
        }

        List<TagCountsM> services = DbWaterLogApi.getSqlServicesByLogger(logger, tag_name);

        if (Utils.isEmpty(serviceName)) {
            if (services.size() > 0) {
                serviceName = services.get(0).tag;
            }
        }

        viewModel.put("tabs", services);
        viewModel.put("tag_name", tag_name);

        //////

        List<TagCountsM> secondList = DbWaterLogApi.getSqlSecondsTags(logger, tag_name, serviceName);

        secondList.sort(Comparator.comparingLong(t->Long.parseLong(t.tag)));

        String method = getSqlMethod(_state);


        long timestamp = 0;
        if (TextUtils.isNotEmpty(time)) {
            timestamp = DateAnalyzer.getGlobal().parse(time.replace("+", " ")).getTime();
        }


        int pageSize = 50;
        List<LogModel> list = DbWaterLogApi.getSqlLogsByPage(logger, tag_name, serviceName, method, seconds, null, null, startId, timestamp);


        viewModel.put("refdate", Datetime.Now().addDay(-2).getDate());

        viewModel.put("pageSize", pageSize);
        viewModel.put("list", list);
        viewModel.put("listSize", list.size());
        viewModel.put("secondList", secondList);
        viewModel.put("serviceName", serviceName);

        if (list.size() > 0) {
            viewModel.put("lastId", list.get(list.size() - 1).log_id);
        } else {
            viewModel.put("lastId", 0L);
        }

        return view("mot/sql_inner");
    }

    private String getSqlMethod(Integer _state) {
        if (_state != null) {
            switch (_state) {
                case 1:
                    return "SELECT";
                case 2:
                    return "UPDATE";
                case 3:
                    return "INSERT";
                case 4:
                    return "DELETE";
                case 5:
                    return "OTHER";
            }
        }

        return null;
    }
}