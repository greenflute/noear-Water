package wateraide.controller.init;

import org.noear.solon.Utils;
import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.annotation.Post;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.Result;
import org.noear.water.WW;
import org.noear.water.utils.Base64Utils;
import org.noear.weed.DbContext;
import wateraide.Config;
import wateraide.dso.InitUtils;
import wateraide.dso.db.DbWaterCfgApi;

import java.util.Properties;

/**
 * @author noear 2021/11/2 created
 */
@Controller
public class Init1WaterDbController {
    @Post
    @Mapping("/ajax/init/water")
    public Result ajax_init(Context ctx) throws Exception {
        if (Config.water == null) {
            return Result.failure("未连接数据库，刷新再试...");
        }

        String config = ctx.cookie("TOKEN");
        if (Utils.isNotEmpty(config)) {
            config = Base64Utils.decode(config);
        }

        Properties props = Utils.buildProperties(config);

        tryInitSchema(Config.water, props);

        //1.
        return Result.succeed(null, "初始化成功");
    }

    private void tryInitSchema(DbContext db, Properties props) throws Exception {
        if (InitUtils.allowWaterInit(db) == false) {
            DbWaterCfgApi.updConfig(WW.water, Config.water_setup_step, "1");
            return;
        }


        db.setAllowMultiQueries(true);

        InitUtils.tryInitWater(db);
        InitUtils.tryInitWaterBcf(db);

        Config.checkProp(props);

        String schema = props.getProperty("schema");
        String password = props.getProperty("password");
        String username = props.getProperty("username");
        String url = props.getProperty("url");


        //更新配置
        StringBuilder cfgBuf = new StringBuilder();
        cfgBuf.append("schema=").append(schema).append("\n");
        cfgBuf.append("url=").append(url).append("\n");
        cfgBuf.append("password=").append(password).append("\n");
        cfgBuf.append("username=").append(username).append("\n");
        cfgBuf.append("jdbcUrl=${url}\n");

        DbWaterCfgApi.updConfig(WW.water, WW.water, cfgBuf.toString());
        DbWaterCfgApi.updConfig(WW.water, Config.water_setup_step, "1");
    }
}